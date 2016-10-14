/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.tracking.BugTrackingSystemRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class ReadSavedBugZillaRepositoryTest {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream(
                    "/home/sesa/Scrivania/Michele_Tufano/files/log_bug_read")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadSavedBugZillaRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Process process = new Process();
        process.initIssueTrackerRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/bugZillaRepository.data", "bugzilla");
        BugTrackingSystemRepository issueRepository = process.getIssueRepository();
        System.out.println("Num Bug: " + issueRepository.getBugs().size());
        issueRepository.printAllBugs();
    }

}
