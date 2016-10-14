/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class ProcessTest {

    public static void main(String[] args) throws IOException {

        try {
            System.setOut(new PrintStream(new FileOutputStream("/home/sesa/Scrivania/Michele_Tufano/files/log")));
            System.setErr(new PrintStream(new FileOutputStream("/home/sesa/Scrivania/Michele_Tufano/files/log_err")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        Process process = new Process();
        process.initGitRepository("/home/sesa/Scrivania/Michele_Tufano/projects/ant/");
        process.initIssueTrackerRepository("https://issues.apache.org/bugzilla/", "Ant", "bugzilla", false);
        process.stats();

    }

}
