package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.algorithm.SZZ;
import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dario
 */
final class CalculateBuggyFiles {

    private final List<FileBean> buggyFiles;

    public CalculateBuggyFiles(String scatteringFolderPath, String projectName, String issueTracker, String issueTrackerPath, String productName, boolean initIssueTracker, boolean initRepository, boolean isSVN) throws IOException {
        System.out.println("Start CalculateBuggyFiles");
        File buggyFilesFile = new File(scatteringFolderPath + File.separator + projectName + File.separator + "buggyFiles.data");
        if (!buggyFilesFile.exists()) {
            this.initBuggyFiles(scatteringFolderPath, issueTracker, issueTrackerPath, projectName, productName, initRepository, initIssueTracker, isSVN);
        }
        this.buggyFiles = this.loadBuggyFiles(scatteringFolderPath, projectName);
    }

    private void initBuggyFiles(String scatteringFolderPath, String issueTracker, String issueTrackerPath, String projectName, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN) throws IOException {
        Process process = new Process();
        if (initRepository) {
            process.initGitRepository(scatteringFolderPath + projectName);
            process.saveGitRepository(scatteringFolderPath + projectName + "/gitRepository.data");
        } else {
            process.initGitRepositoryFromFile(scatteringFolderPath + projectName + "/gitRepository.data");
        }

        if (initIssueTracker) {
            process.initIssueTrackerRepository(issueTrackerPath, productName, issueTracker, isSVN);
            process.saveIssueTrackerRepository(scatteringFolderPath + projectName + "/issueRepository.data");
        } else {
            process.initIssueTrackerRepositoryFromFile(scatteringFolderPath + projectName + "/issueRepository.data", issueTracker);
        }

        //Run szz
        this.runSZZ(process);

        //Save results
        this.saveBuggyFiles(scatteringFolderPath, projectName, process);
    }

    private void runSZZ(Process process) {
        System.out.println("\nStart SZZ");

        for (Bug bug : process.getIssueRepository().getBugs()) {
            SZZ.identifyBugFixWithStandardModel(bug, process.getGitRepository().getCommits());
            SZZ.locateFixInducingChanges(bug, process.getGitRepository());
        }

        System.out.println("End SZZ");
    }

    private void saveBuggyFiles(String scatteringFolderPath, String projectName, Process process) throws IOException {
        List<Commit> commits = process.getGitRepository().getCommits();
        List<FileBean> files = new ArrayList<>();
        for (Commit commit : commits) {
            if (commit.isBug()) {
                Bug bug = commit.getIntroducedBug();
                Commit fixCommit = bug.getFix();
                for (Change change : fixCommit.getChanges()) {
                    files.add(change.getFile());
                }
            }
        }

        File file = new File(scatteringFolderPath + projectName + "/buggyFiles.data");
        try (FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            for (FileBean f : files) {
                out.writeObject(f);
            }
        }

        System.out.println(
                "Serialized " + files.size() + " element is saved in " + file);
    }

    private List<FileBean> loadBuggyFiles(String scatteringFolderPath, String projectName) {
        List<FileBean> buggyFilesList = new ArrayList<>();
        File file = new File(scatteringFolderPath + projectName + "/buggyFiles.data");
        try {
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                FileBean f;
                while ((f = (FileBean) in.readObject()) != null) {
                    buggyFilesList.add(f);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CalculateBuggyFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buggyFilesList;
    }

    public List<FileBean> getBuggyFiles() {
        return buggyFiles;
    }

}
