package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.scattering.DeveloperTreeManager;
import it.unisa.gitdm.scattering.PeriodManager;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Checkout {

    public Checkout(String projectName, String periodLength, String baseFolderPath, String scatteringFolderPath, boolean init) {

        System.out.println("Start checkout");
        Process process = new Process();

        if (init == true) {
            process.initGitRepository(baseFolderPath + projectName);
            File scatteringProjectFolder = new File(scatteringFolderPath + projectName);
            scatteringProjectFolder.mkdirs();
            process.saveGitRepository(scatteringFolderPath + File.separator + projectName + File.separator + "gitRepository.data");
        } else {
            process.initGitRepositoryFromFile(scatteringFolderPath + File.separator + projectName + File.separator + "gitRepository.data");
        }
        File developerChangesPath = new File(scatteringFolderPath + File.separator + projectName + File.separator
                + periodLength + File.separator);
        developerChangesPath.mkdirs();
        File developersChanges = new File(scatteringFolderPath + File.separator + projectName + File.separator
                + periodLength + File.separator + "developersChanges.data");
        System.out.println("Init done");
        try (OutputStream file = new FileOutputStream(developersChanges);
                ObjectOutput output = new ObjectOutputStream(file)) {
            List<Commit> commits = process.getGitRepository().getCommits();
            System.out.println("Number of Commits: " + commits.size());
            PeriodManager.calculatePeriods(commits, periodLength);
            DeveloperTreeManager.calculateDeveloperTrees(commits, periodLength);
            output.writeObject(DeveloperTreeManager.getDeveloperTrees());
        } catch (IOException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Data saved in " + scatteringFolderPath + File.separator + projectName + File.separator + "developersChanges.data");
    }
}
