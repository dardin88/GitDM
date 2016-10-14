package it.unisa.gitdm.experiments;

import java.io.IOException;

class CompleteScatteringExperiment {

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
        CompleteScatteringExperiment.mainForARepo("lenya", "3m", "/home/sesa/Development/Repo/",
                "/home/sesa/Development/scattering/", false, true, "bugzilla", "Lenya", bugzillaUrl, false);
    }

    private static void mainForARepo(String projectName, String periodLength,
                                     String baseFolderPath, String scatteringFolderPath, boolean initRepository, boolean initIssueTracker,
                                     String issueTracker, String productName, String issueTrackerPath, boolean isSVN) throws IOException {

        //Git.clone(repoURL, isSVN, projectName, baseFolder);
        Checkout checkout = new Checkout(projectName, periodLength, baseFolderPath, scatteringFolderPath, initRepository);
        CalculateDeveloperStructuralScattering calculateDeveloperStructuralScattering = new CalculateDeveloperStructuralScattering(projectName, periodLength, scatteringFolderPath);
        CalculateDeveloperSemanticScattering calculateDeveloperSemanticScattering = new CalculateDeveloperSemanticScattering(projectName, periodLength, baseFolderPath, scatteringFolderPath);
        CalculateBuggyFiles calculateBuggyFiles = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, initIssueTracker, initRepository, isSVN);
        CalculatePredictors calculatePredictors = new CalculatePredictors(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolderPath, scatteringFolderPath);
        EvaluateMetrics em = new EvaluateMetrics(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolderPath, scatteringFolderPath);
    }
}
