/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.BugFixing;
import it.unisa.gitdm.algorithm.ProcessAverage;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateBugFixing {

    public static void main(String[] args) {

        ProcessAverage process = new ProcessAverage();
        process.initBugZillaRepositoryFromFile("/home/michele/Scrivania/Michele_Tufano/files/bugZillaRepository.data");
        process.initGitRepositoryFromFile("/home/michele/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");

        BugFixing bugFixing = new BugFixing(process.getGitRepository());

        bugFixing.identifyWhoFixBug();
    }

}
