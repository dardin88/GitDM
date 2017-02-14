package it.unisa.gitdm.experiments;

import java.io.IOException;

class ExtractCompleteSZZDataset {

    public static void main(String[] args) throws IOException {
        String bugzillaUrl = "https://issues.apache.org/bugzilla/";
        String jiraUrl = "https://issues.apache.org/jira/";
//        String camelURL = "https://git-wip-us.apache.org/repos/asf/camel.git";
//        String falconURL = "https://git-wip-us.apache.org/repos/asf/falcon.git";
//        String jpaURL = "https://svn.apache.org/repos/asf/openjpa/trunk";
//        String whirrURL = "git://git.apache.org/whirr.git";
//        String rangerURL = "git://git.apache.org/incubator-ranger.git";
//        String oakURL = "https://svn.apache.org/repos/asf/jackrabbit/oak/trunk";
//        String antURL = "https://git-wip-us.apache.org/repos/asf/ant.git";
        String lenyaURL = "https://github.com/apache/lenya.git";

        //FATTO!
        ExtractCompleteSZZDataset.mainForARepo("amq", "3m", "/home/dardin88/Desktop/emse_repo/",
                "/home/dardin88/Desktop/emse_data/", true, true, "jira", "AMQ", jiraUrl, false);
    }

    private static void mainForARepo(String projectName, String periodLength,
            String baseFolderPath, String scatteringFolderPath, boolean initRepository, boolean initIssueTracker,
            String issueTracker, String productName, String issueTrackerPath, boolean isSVN) throws IOException {
        //Checkout checkout = new Checkout(projectName, periodLength, baseFolderPath, scatteringFolderPath, initRepository);
        CalculateBuggyFiles calculateBuggyFiles = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, isSVN);
        CalculatePredictors calculatePredictors = new CalculatePredictors(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolderPath, scatteringFolderPath);
    }
}
