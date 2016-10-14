/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class Step2_InitAndSaveBugZillaRepository {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream(
                    "/home/sesa/Scrivania/Michele_Tufano/files/log")));
            System.setErr(new PrintStream(new FileOutputStream(
                    "/home/sesa/Scrivania/Michele_Tufano/files/log_err")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepository.data");
        process.initIssueTrackerRepository("https://issues.apache.org/bugzilla/", "Lenya", "bugzilla", false);
        process.saveIssueTrackerRepository("/home/sesa/Scrivania/Michele_Tufano/files/bugZillaRepository.data");
        process.saveGitRepository("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");
    }

}
