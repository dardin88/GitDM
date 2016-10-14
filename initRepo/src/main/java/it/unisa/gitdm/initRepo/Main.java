package it.unisa.gitdm.initRepo;

import it.unisa.gitdm.bean.Developer;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.evaluation.WekaEvaluator;
import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import it.unisa.gitdm.source.Git;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String repoURL = "https://github.com/apache/ant.git";
        //String repoURL = "https://github.com/fabianopecorelli/provaPerTesi.git";
        String projectName = "ant";
        String where = "/home/fabiano/Desktop/gitdm/";
        String scatteringFolder = "/home/fabiano/Desktop/gitdm/scattering/";
        String issueTracker = "bugzilla";
        String bugzillaUrl = "https://issues.apache.org/bugzilla/";
        
        
        Main.initAndCheckout(repoURL, where, projectName,"All", scatteringFolder, issueTracker, bugzillaUrl, "Ant", true, true, false);
    }
    
    public static void initAndCheckout(String repoURL, String baseFolder, String projectName, String periodLength,
                                     String scatteringFolderPath, String issueTracker, String issueTrackerPath, String productName, boolean initRepository, boolean initIssueTracker, boolean isSVN) throws IOException, InterruptedException{
//        Git.clone(repoURL, isSVN, projectName, baseFolder);
//        Checkout checkout = new Checkout(projectName, periodLength, baseFolder, scatteringFolderPath, initRepository);
//        CalculateDeveloperStructuralScattering calculateDeveloperStructuralScattering = new CalculateDeveloperStructuralScattering(projectName, periodLength, scatteringFolderPath);
//        CalculateDeveloperSemanticScattering calculateDeveloperSemanticScattering = new CalculateDeveloperSemanticScattering(projectName, periodLength, baseFolder, scatteringFolderPath);
//        CalculateBuggyFiles calculateBuggyFiles = new CalculateBuggyFiles(scatteringFolderPath, projectName, issueTracker, issueTrackerPath, productName, initIssueTracker, false, isSVN);
        CalculatePredictors calculatePredictors = new CalculatePredictors(projectName, issueTracker, issueTrackerPath, productName, periodLength, baseFolder, scatteringFolderPath);
        WekaEvaluator we = new WekaEvaluator("/home/fabiano/Desktop/example.arff");
    }
}
