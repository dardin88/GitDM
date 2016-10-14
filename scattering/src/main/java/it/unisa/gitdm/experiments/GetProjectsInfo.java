package it.unisa.gitdm.experiments;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.source.Git;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.*;
import java.util.List;

class GetProjectsInfo {

    public static void main(String[] args) throws IOException {
        String bugzillaUrl = "https://issues.apache.org/bugzilla/";
        String jiraUrl = "https://issues.apache.org/jira/";
        String baseFolderPath = "D:\\Desktop\\scattering_repo\\";
        String scatteringFolderPath = "D:\\Desktop\\scattering\\";

        GetProjectsInfo.mainForARepo("camel", "3m", baseFolderPath, scatteringFolderPath, true, true, "jira", "CAMEL", jiraUrl, false);
    }

    private static void mainForARepo(String projectName, String periodLength,
                                     String baseFolderPath, String scatteringFolderPath, boolean initRepository, boolean initIssueTracker,
                                     String issueTracker, String productName, String issueTrackerPath, boolean isSVN) throws IOException {

        File scatteringFolder = new File(scatteringFolderPath);
        scatteringFolder.mkdirs();
        File projectFolder = new File(baseFolderPath + "/" + projectName);
        projectFolder.mkdirs();

        Checkout checkout = new Checkout(projectName, periodLength, baseFolderPath, scatteringFolderPath, initRepository);
        Process process = new it.unisa.gitdm.algorithm.Process();
        process.initGitRepositoryFromFile(baseFolderPath + projectName + "/gitRepository.data");
        CalculateBuggyFiles calculateBuggyFiles = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, initIssueTracker, initRepository, isSVN);

        GitRepository gitRepository = process.getGitRepository();
        List<Commit> commits = gitRepository.getCommits();
        int middleCommitNumber = commits.size() / 2;
        Commit c = commits.get(middleCommitNumber);
        Git.gitReset(projectFolder);
        Git.clean(projectFolder);
        Git.gitCheckout(projectFolder, c, scatteringFolder);

        List<FileBean> repoFiles = Git.gitList(projectFolder, c, scatteringFolder);

        int numberOfAuthors = gitRepository.getAuthorRepository().getAuthors().size();

        int numberOfLines = 0;
        for (FileBean fb : repoFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(fb.getPath()))) {
                while (br.readLine() != null) {
                    numberOfLines++;
                }
            }
        }

        int numberOfClasses = 0;
        for (FileBean fb : repoFiles) {
            if (fb.getPath().contains(".java")) {
                numberOfClasses++;
            }
        }

        int numberOfBuggyFiles = calculateBuggyFiles.getBuggyFiles().size();

        try (PrintWriter writer = new PrintWriter(baseFolderPath + "systems_info.csv", "UTF-8")) {
            writer.println(projectName + "," + commits.size() + "," + numberOfAuthors + "," + numberOfClasses + "," + numberOfLines + "," + numberOfBuggyFiles);
        }
    }
}
