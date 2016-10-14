package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.algorithm.ScatteringEntropy;
import it.unisa.gitdm.bean.ChangedClass;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.scattering.PeriodManager;
import it.unisa.gitdm.scattering.PeriodsListNonInitialized;

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
public class CalculateDeveloperSemanticScattering {

    public CalculateDeveloperSemanticScattering(String projectName, String periodLength,
                                                String baseFolderPath, String scatteringFolderPath) {

        Process process = new Process();

        process.initGitRepositoryFromFile(scatteringFolderPath + projectName
                + "/gitRepository.data");

        ScatteringEntropy entropy = new ScatteringEntropy(process.getGitRepository());

        File developersChangesFile = new File(scatteringFolderPath + projectName
                + "/" + periodLength + "/developersChanges.data");

        File semanticScatteringFile = new File(scatteringFolderPath + projectName + "/"
                + periodLength + "/semanticScattering.csv");

        try (
                InputStream input = new FileInputStream(developersChangesFile);
                ObjectInput objectInput = new ObjectInputStream(input)) {

            List<DeveloperTree> devTrees = (List<DeveloperTree>) objectInput.readObject();

            List<Commit> commits = process.getGitRepository().getCommits();
            PeriodManager.calculatePeriods(commits, periodLength);

            try (PrintWriter pw = new PrintWriter(semanticScatteringFile)) {
                for (DeveloperTree devTree : devTrees) {

                    if (devTree.getPeriod() != -1) {

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

                        entropy.setBaseFolder(baseFolderPath);
                        entropy.setProjectName(projectName);

                        double developerSemanticScattering = entropy.calculateDeveloperSemanticScattering(devTree);

                        pw.write(devTree.getDeveloper().getEmail() + ","
                                + devTree.getPeriod() + "," + changes + ","
                                + differentFilesChanged.size() + ","
                                + developerSemanticScattering + "\n");
                    }
                }
            } catch (FileNotFoundException | PeriodsListNonInitialized ex) {
                Logger.getLogger(CalculateDeveloperSemanticScattering.class.getName()).log(Level.SEVERE, null, ex);
            }
            objectInput.close();
            System.out.println("Data written in: semanticScattering.csv");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CalculateDeveloperSemanticScattering.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
