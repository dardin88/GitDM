/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.versioning.GitRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class InitRepositoryTest {

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        try {
            System.setOut(new PrintStream(new FileOutputStream("/home/michele/Scrivania/log_init_repository")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        GitRepository repository = new GitRepository();

        File directory = new File("/home/michele/ant/");
        //File directory = new File("/home/michele/AFNetworking/");
        //File xx = new File("/home/michele/S");

        repository.setDirectory(directory);

        repository.initialize();

        System.out.println("Num of commits: " + repository.getCommits().size());

        repository.printAllCommits();
    }

}
