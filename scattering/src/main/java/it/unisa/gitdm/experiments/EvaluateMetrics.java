package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.CommitGoalTagger;
import it.unisa.gitdm.algorithm.Ownership;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.*;
import it.unisa.gitdm.metrics.CKMetrics;
import it.unisa.gitdm.metrics.HistoricalMetrics;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Giuderos
 */
class EvaluateMetrics {

    public EvaluateMetrics(String projectName, String issueTracker, String issueTrackerPath,
                           String productName, String periodLength, String baseFolderPath,
                           String scatteringFolderPath) {

        Process process = new Process();
        process.initGitRepositoryFromFile(scatteringFolderPath + projectName
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
                        + "/metrics/";

                File periodsFolder = new File(periodsFolderPath);
                periodsFolder.mkdirs();

                String periodString = Integer.toString(p.getId());
                if (p.getId() < 10) {
                    periodString = "0" + p.getId();
                }
                File periodData = new File(periodsFolderPath + periodString + ".csv");

                PrintWriter pw1 = new PrintWriter(periodData);
                pw1.write("name,"
                        + "LOC,CBO,LCOM,NOM,RFC,WMC,ACS,Ownership,"
                        + "numOfChanges,numberOfFIChanges,ENH,NF,BugFixing,REF,"
                        + "structuralScatteringSum,semanticScatteringSum,"
                        + "numberOfDeveloper,pastFaults,MAF,"
                        + "isBuggy\n");

                CalculateBuggyFiles cbf = new CalculateBuggyFiles(scatteringFolderPath,
                        projectName, issueTracker, issueTrackerPath, productName,
                        false, false, false);
                List<FileBean> periodBuggyFiles = cbf.getBuggyFiles();


                String projectPath = baseFolderPath + projectName;
                File workTreeFolder = new File(baseFolderPath + "wd");
                FileUtils.deleteDirectory(workTreeFolder);
                workTreeFolder.mkdirs();

                int middleCommitNumber = p.getCommits().size() / 2;

                Commit c = p.getCommits().get(middleCommitNumber);

                Git.gitReset(new File(projectPath));
                Git.clean(new File(projectPath));
                Git.gitCheckout(new File(projectPath), c, workTreeFolder);

                List<FileBean> repoFiles = Git.gitList(new File(projectPath), c, workTreeFolder);

                for (FileBean file : repoFiles) {
                    double LOC = 0;
                    double CBO = 0;
                    double LCOM = 0;
                    double NOM = 0;
                    double RFC = 0;
                    double WMC = 0;
                    double ACS;

                    if (file.getPath().contains(".java")) {
                        File workTreeFile = new File(workTreeFolder + "/" + file.getPath());

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

                            List<Developer> developersOnFile = DeveloperTreeManager
                                    .getDevelopersOnFile(file,
                                            p.getId());

                            for (Developer developer : developersOnFile) {
                                double developerStructuralScattering = structuralMP.getMetrics(developer.getEmail(), p.getId(),
                                        ScatteringType.AVERAGE).getValue();
                                double developerSemanticScattering = semanticalMP.getMetrics(developer.getEmail(), p.getId(),
                                        ScatteringType.AVERAGE).getValue();

                                numberOfChanges += DeveloperTreeManager
                                        .getNumberOfChanges(developer,
                                                p.getId(), file);

                                numberOfFIChanges += DeveloperFITreeManager
                                        .getNumberOfChanges(developer,
                                                p.getId(), file);

                                structuralFileScattering += developerStructuralScattering;
                                semanticFileScattering += developerSemanticScattering;
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

                            int lastIndex = file.getPath().lastIndexOf("/");
                            ACS = HistoricalMetrics.averageCommitSize(file.getPath().substring(lastIndex),
                                    p.getCommits());
                            String OWN = this.getOwner(file.getPath(), process);
                            double ENH = this.getNumberOfEnhancements(file.getPath(), process, p);
                            double NF = this.getNumberOfNewFeatures(file.getPath(), process, p);
                            double BF = this.getNumberOfBugFixing(file.getPath(), process, p);
                            double REF = this.getNumberOfRefactoring(file.getPath(), process, p);
                            int pastFault = this.getNumberOfPastFault(file.getPath(), process, p, periods);
                            double MAF = this.getPosnett(file.getPath(), process, p);

                            message1 += file.getPath() + ","
                                    + LOC + "," + CBO + "," + LCOM + "," + NOM + ","
                                    + RFC + "," + WMC + ","
                                    + ACS + "," + OWN + "," + numberOfChanges + ","
                                    + numberOfFIChanges + "," + Math.rint(ENH) + ","
                                    + Math.rint(NF) + "," + Math.rint(BF) + "," + Math.rint(REF) + ","
                                    + structuralFileScattering + "," + semanticFileScattering + ","
                                    + developersOnFile.size() + "," + pastFault + "," + MAF + ","
                                    + isBuggy + "\n";
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

    private double getNumberOfEnhancements(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double enhancement = 0;
        double totalCommit = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                totalCommit = fileMap.getValue().size();
                for (Commit c : fileMap.getValue()) {
                    //System.out.println("File: " +filePath + "FileMap: " + fileMap.getKey() + "Commit: "+c);
                    if (p.getCommits().contains(c) && CommitGoalTagger.isEnhancement(c)) {
                        enhancement++;
                    }

                }
            }
        }
        if (enhancement > 0)
            return (enhancement / totalCommit) * 100;
        return 0;
    }

    private double getNumberOfNewFeatures(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double newFeatures = 0;
        double totalCommit = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                totalCommit = fileMap.getValue().size();
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isNewFeature(c))
                        newFeatures++;
                }
            }
        }
        if (newFeatures > 0)
            return (newFeatures / totalCommit) * 100;
        return 0;
    }

    private double getNumberOfBugFixing(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double bugFixing = 0;
        double totalCommit = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                totalCommit = fileMap.getValue().size();
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isBugFixing(c))
                        bugFixing++;
                }
            }
        }
        if (bugFixing > 0)
            return (bugFixing / totalCommit) * 100;
        return 0;
    }

    private double getNumberOfRefactoring(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double refactoring = 0;
        double totalCommit = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                totalCommit = fileMap.getValue().size();
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isRefactoring(c))
                        refactoring++;
                }
            }
        }
        if (refactoring > 0)
            return (refactoring / totalCommit) * 100;
        return 0;
    }

    private String getOwner(String filePath, Process process) {
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        String own = null;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                HashMap<Author, Integer> authors = ownership.calculateAuthorsOnFile(fileMap);
                Author owner = ownership.findOwner(authors, 40);
                if (owner != null)
                    own = owner.getEmail();
            }
        }
        return own;
    }

    private int getNumberOfPastFault(String filePath, Process process, Period p, List<Period> periods) {
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int pastFault = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Period per : periods) {
                    if (per.getId() < p.getId()) {
                        //System.out.println("Periodo " + per.getId() + "\nCommit del Periodo: " + per.getCommits().size());
                        for (Commit c : per.getCommits()) {
                            if (fileMap.getValue().contains(c) && CommitGoalTagger.isBugFixing(c)) {
                                //System.out.println("is Bug!");
                                pastFault++;
                            }
                        }
                    }

                }
            }
        }
        return pastFault;
    }

    private double getPosnett(String filePath, Process process, Period p) {
        //System.out.println("FILE:"+filePath);
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double totalCommit = 0;
        double MAF = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                HashMap<Author, Integer> authors = ownership.calculateAuthorsOnFile(fileMap);
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c)) {
                        totalCommit++;
                    }
                }
                double firstExpr = 0;
                double secondExpr = 0;
                for (Author a : authors.keySet()) {
                    double authorCommit = 0;
                    for (Commit c : fileMap.getValue()) {
                        if (p.getCommits().contains(c)) {
                            if (c.getAuthor().equals(a)) {
                                authorCommit++;
                            }
                        }
                    }
                    if (authorCommit == 0 || p.getCommits(a).isEmpty()) {
                        continue;
                    }
                    //System.out.println("Author:"+a+"\nAuthorCommit:"+authorCommit+"\ntotalCommit:"+totalCommit);
                    double wij = authorCommit / totalCommit;
                    //System.out.println("wij: "+wij);
                    if (Double.isNaN(wij))
                        wij = 0;
                    double commitsAuthorSize = p.getCommits(a).size();
                    double commitSize = p.getCommits().size();
                    //System.out.println("Dj:"+commitsAuthorSize +"\nA:"+ commitSize);
                    //System.out.println("ln: " + Math.log(commitsAuthorSize/commitSize));
                    double ln = Math.log(commitsAuthorSize / commitSize);
                    firstExpr = firstExpr + (wij * ln);
                    secondExpr = secondExpr + (wij * Math.log(wij));

                }
                MAF = (-firstExpr) - (-secondExpr);
                //System.out.println("MAF:"+ MAF);    
            }
        }
        return MAF;
    }
}