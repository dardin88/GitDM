package it.unisa.gitdm.initRepo;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.*;
import it.unisa.gitdm.metrics.CKMetrics;
import it.unisa.gitdm.metrics.ReadSourceCode;
import it.unisa.gitdm.metrics.parser.bean.ClassBean;
import it.unisa.gitdm.scattering.DeveloperFITreeManager;
import it.unisa.gitdm.scattering.DeveloperTreeManager;
import it.unisa.gitdm.scattering.PeriodManager;
import it.unisa.gitdm.scattering.ScatteringMetricsParser;
import it.unisa.gitdm.source.Git;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class CalculatePredictors {

    public CalculatePredictors(String projectName, String issueTracker, String issueTrackerPath,
                               String productName, String periodLength, String baseFolderPath,
                               String scatteringFolderPath) {

        Process process = new Process();

        process.initGitRepositoryFromFile(scatteringFolderPath + "/" + projectName
                + "/gitRepository.data");

        // Calculate developer trees
        try {
            DeveloperTreeManager.calculateDeveloperTrees(process.getGitRepository()
                    .getCommits(), periodLength);
            DeveloperFITreeManager.calculateDeveloperTrees(process.getGitRepository()
                    .getCommits(), periodLength);
        } catch (Exception ex) {
            Logger.getLogger(CalculatePredictors.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Calculate periods
        List<Commit> commits = process.getGitRepository().getCommits();
        PeriodManager.calculatePeriods(commits, periodLength);
        List<Period> periods = PeriodManager.getList();

        // Load files and folders
        ScatteringMetricsParser structuralMP = new ScatteringMetricsParser();
        ScatteringMetricsParser semanticalMP = new ScatteringMetricsParser();
        File structuralScatteringFile = new File(scatteringFolderPath + projectName + "/"
                + periodLength + "/structuralScattering.csv");
        File semanticScatteringFile = new File(scatteringFolderPath + projectName + "/"
                + periodLength + "/semanticScattering.csv");

        // Load developer scattering metrics
        try {
            structuralMP.parseFile(structuralScatteringFile);
            semanticalMP.parseFile(semanticScatteringFile);

            // Calculating file complexity
            for (Period p : periods) {

                String periodsFolderPath = scatteringFolderPath + projectName + "/" + periodLength
                        + "/periodsData_1/";

                File periodsFolder = new File(periodsFolderPath);
                periodsFolder.mkdirs();
                String periodString = Integer.toString(p.getId());
                if (p.getId() < 10) {
                    periodString = "0" + p.getId();
                }
                File periodData = new File(periodsFolderPath + periodString + ".csv");
                PrintWriter pw1 = new PrintWriter(periodData);

                pw1.write("name,"
                        + "LOC,CBO,LCOM,NOM,RFC,WMC,"
                        + "numOfChanges," + "numberOfFIChanges,"
                        + "structuralScatteringSum,semanticScatteringSum,"
                        + "numberOfDeveloper," + "isBuggy\n");

                CalculateBuggyFiles cbf = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, false, false, false);

                List<FileBean> periodBuggyFiles = cbf.getBuggyFiles();
                
                String projectPath = baseFolderPath + projectName;
                File workTreeFolder = new File(baseFolderPath + "wd");
                FileUtils.deleteDirectory(workTreeFolder);
                workTreeFolder.mkdirs();

               // int middleCommitNumber = p.getCommits().size() / 2;

             //   Commit c = p.getCommits().get(middleCommitNumber);

                Git.gitReset(new File(projectPath));
                Git.clean(new File(projectPath));
           //     Git.gitCheckout(new File(projectPath), c, workTreeFolder);

                List<FileBean> repoFiles = Git.gitList(new File(projectPath));
                System.out.println("Repo size: "+repoFiles.size());
                for (FileBean file : repoFiles) {
                    double LOC = 0;
                    double CBO = 0;
                    double LCOM = 0;
                    double NOM = 0;
                    double RFC = 0;
                    double WMC = 0;

                    if (file.getPath().contains(".java")) {
                        File workTreeFile = new File(projectPath + "/" + file.getPath());

                        ClassBean classBean;

                        if (workTreeFile.exists()) {
                            ArrayList<ClassBean> code = new ArrayList<>();
                            ArrayList<ClassBean> classes = ReadSourceCode.readSourceCode(workTreeFile, code);

                            if (classes.size() > 0) {
                                classBean = classes.get(0);

                                LOC = CKMetrics.getLOC(classBean);
                                CBO = CKMetrics.getCBO(classBean);
                                LCOM = CKMetrics.getLCOM(classBean);
                                NOM = CKMetrics.getNOM(classBean);
                                RFC = CKMetrics.getRFC(classBean);
                                WMC = CKMetrics.getWMC(classBean);
                            }

                            double structuralFileScattering = 0;

                            double semanticFileScattering = 0;

                            int numberOfChanges = 0;
                            double numberOfFIChanges = 0;
                            String message1 = "";

                            List<Developer> developersOnFile = DeveloperTreeManager.getDevelopersOnFile(file, p.getId());

                            for (Developer developer : developersOnFile) {
                                double structuralScattering = structuralMP.getMetrics(developer.getEmail(), p.getId(),
                                        ScatteringType.AVERAGE).getValue();
                                double semanticScattering = semanticalMP.getMetrics(developer.getEmail(), p.getId(),
                                        ScatteringType.AVERAGE).getValue();

                                numberOfChanges += DeveloperTreeManager
                                        .getNumberOfChanges(developer,
                                                p.getId(), file);

                                numberOfFIChanges += DeveloperFITreeManager
                                        .getNumberOfChanges(developer,
                                                p.getId(), file);

                                structuralFileScattering += structuralScattering;
                                semanticFileScattering += semanticScattering;
                            }
                            boolean isBuggy = false;

                            for (FileBean fileBean : periodBuggyFiles) {
                                if (fileBean.getPath().equals(
                                        file.getPath())) {
                                    isBuggy = true;
                                    break;
                                }
                            }

                            int totalNumberOfChanges = this.getTotalNumberOfChanges(p);

                            if (totalNumberOfChanges > 0) {
                                numberOfFIChanges /= totalNumberOfChanges;
                            }

                            message1 += file.getPath() + ","
                                    + LOC + "," + CBO + "," + LCOM + "," + NOM + "," + RFC + "," + WMC + ","
                                    + numberOfChanges + "," + numberOfFIChanges + ","
                                    + structuralFileScattering + "," + semanticFileScattering + ","
                                    + developersOnFile.size() + "," + isBuggy + "\n";

                            pw1.write(message1);
                        }
                    }
                }
                Git.gitReset(new File(projectPath));
                pw1.flush();

                System.out.println(periodData);
            }
        } catch (NumberFormatException | IOException ex) {
            Logger.getLogger(CalculatePredictors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getTotalNumberOfChanges(Period p) {
        int totalNumberOfChanges = 0;

        List<FileBean> periodFiles = PeriodManager.getFiles(p.getId());

        for (FileBean periodFileBean : periodFiles) {
            if (periodFileBean.getPath().contains(".java")) {

                List<Developer> developersOnFile = DeveloperTreeManager
                        .getDevelopersOnFile(periodFileBean,
                                p.getId());

                for (Developer developer : developersOnFile) {
                    totalNumberOfChanges += DeveloperTreeManager
                            .getNumberOfChanges(developer,
                                    p.getId(), periodFileBean);
                }
            }
        }
        return totalNumberOfChanges;
    }
}
