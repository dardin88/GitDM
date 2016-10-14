/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.ProcessAverage;

import java.io.File;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class StatsTest {

    public static void main(String[] args) {

        ProcessAverage process = new ProcessAverage();
        process.initBugZillaRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/bugZillaRepository.data");
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");

        process.getGitRepository().setDirectory(new File("/home/sesa/Scrivania/Michele_Tufano/projects/tomcat70"));

        process.stats();
    }

}
