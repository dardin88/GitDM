/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class ReadSavedGitRepositoryTest {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(
                            "/home/sesa/Scrivania/Michele_Tufano/files/log_commit_read")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepository.data");
        GitRepository gitRepository = process.getGitRepository();
        System.out.println("Num Commits: " + gitRepository.getCommits().size());
        gitRepository.printAllCommits();
    }

}
