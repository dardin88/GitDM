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
class Step1_InitAndSaveGitRepositoryTest {

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
        process.initGitRepository("/home/sesa/Scrivania/Michele_Tufano/projects/lenya/");
        process.filterGitRepository(50);
        process.saveGitRepository("/home/sesa/Scrivania/Michele_Tufano/files/gitRepository.data");
    }

}
