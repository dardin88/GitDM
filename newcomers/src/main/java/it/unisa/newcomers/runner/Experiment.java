/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.newcomers.runner;

import it.unisa.gitdm.experiments.CalculateDeveloperSemanticScattering;
import it.unisa.gitdm.experiments.CalculateDeveloperStructuralScattering;
import it.unisa.gitdm.experiments.Checkout;
import java.io.IOException;

/**
 *
 * @author fabiopalomba
 */
public class Experiment {
    
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


        Experiment.mainForARepo("lenya", "3m", 
                "/Users/fabiopalomba/Documents/PhD/Papers/Working/Others/Newcomers/repositories/",
                "/Users/fabiopalomba/Documents/PhD/Papers/Working/Others/Newcomers/scattering-measurement/", 
                false, true, "bugzilla", "Lenya", bugzillaUrl, false);
    }

    private static void mainForARepo(String projectName, String periodLength,
                                     String baseFolderPath, String scatteringFolderPath, boolean initRepository, boolean initIssueTracker,
                                     String issueTracker, String productName, String issueTrackerPath, boolean isSVN) throws IOException {

        Checkout checkout = new Checkout(projectName, periodLength, baseFolderPath, scatteringFolderPath, initRepository);
        CalculateDeveloperStructuralScattering calculateDeveloperStructuralScattering = new CalculateDeveloperStructuralScattering(projectName, periodLength, scatteringFolderPath);
        CalculateDeveloperSemanticScattering calculateDeveloperSemanticScattering = new CalculateDeveloperSemanticScattering(projectName, periodLength, baseFolderPath, scatteringFolderPath);
       
       // compute oracles: number of days of each developer in the project
       CalculateDeveloperSurvival calculateDeveloperSurvival = new CalculateDeveloperSurvival(projectName, scatteringFolderPath);
       
       // compute developers' scatterings and newcomer informations 
       CalculateScattering calculateScattering = new CalculateScattering(projectName, productName, periodLength, scatteringFolderPath);
    }
    
    
}
