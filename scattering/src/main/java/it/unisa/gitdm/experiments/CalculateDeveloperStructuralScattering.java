package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.DeveloperStructuralFocus;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.ChangedClass;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.scattering.PeriodManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dario Di Nucci - dario.dinucci@hotmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class CalculateDeveloperStructuralScattering {

    public CalculateDeveloperStructuralScattering(String projectName, String periodLength,
                                                  String scatteringFolderPath) {

        Process process = new Process();

        process.initGitRepositoryFromFile(scatteringFolderPath + projectName
                + "/gitRepository.data");

        DeveloperStructuralFocus sf = new DeveloperStructuralFocus(process.getGitRepository());

        File developersChangesFile = new File(scatteringFolderPath + projectName
                + "/" + periodLength + "/developersChanges.data");

        File structuralScatteringFile = new File(scatteringFolderPath + projectName
                + "/" + periodLength + "/structuralScattering.csv");

        try (InputStream input = new FileInputStream(developersChangesFile);
             ObjectInput objectInput = new ObjectInputStream(input)) {
            List<DeveloperTree> devTrees = (List<DeveloperTree>) objectInput.readObject();

            List<Commit> commits = process.getGitRepository().getCommits();
            PeriodManager.calculatePeriods(commits, periodLength);

            try (PrintWriter pw = new PrintWriter(structuralScatteringFile)) {
                for (DeveloperTree devTree : devTrees) {

                    System.out.println("Developer: "
                            + devTree.getDeveloper().getName() + " period: "
                            + devTree.getPeriod());

                    int changes = 0;

                    for (ChangedClass change : devTree.getChangeSet().values()) {
                        changes = changes + change.getNumOfChanges();
                    }

                    List<ChangedClass> differentFilesChanged = new ArrayList<>();

                    for (ChangedClass change : devTree.getChangeSet().values()) {
                        if (!differentFilesChanged.contains(change)) {
                            differentFilesChanged.add(change);
                        }
                    }

                    double developerStructuralScattering = sf
                            .calculateDeveloperStructuralFocus(devTree);

                    pw.write(devTree.getDeveloper().getEmail() + ","
                            + devTree.getPeriod() + "," + changes + ","
                            + differentFilesChanged.size() + ","
                            + developerStructuralScattering + "\n");
                }
            }
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(CalculateDeveloperStructuralScattering.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Data written in: structuralScattering.csv");
    }
}
