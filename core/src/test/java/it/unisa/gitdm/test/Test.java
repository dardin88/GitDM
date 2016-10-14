/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.SZZ;
import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class Test {

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        try {
            System.setOut(new PrintStream(new FileOutputStream(
                    "/home/michele/Scrivania/log")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        GitRepository repository = new GitRepository();

        // File directory = new File("/home/michele/AFNetworking/");
        File directory = new File("/home/michele/Repo GIT/");
        // File xx = new File("/home/michele/S");

        repository.setDirectory(directory);

        repository.initialize();

        System.out.println("Num of commits: " + repository.getCommits().size());

        repository.printAllCommits();

        System.out
                .println(" \n ------------------------------------------------------------------- FINE -------------------------------------------------------------------");
        Bug bug = new Bug();
        bug.setReportedTime(1374401056);
        bug.setFix(repository.getCommits().get(0));

        List<Commit> inducing = SZZ.locateFixInducingChanges(bug, repository);

        System.out.println("INDUCING--------------\n");
        System.out.println("Size: " + inducing.size());

        repository.printCommits(inducing);

        System.out.println("------ author");
        System.out.println(repository.getCommitsByAuthor(
                repository.getCommits().get(0).getAuthor()).size());
        for (Commit c : repository.getCommitsByAuthor(repository.getCommits()
                .get(0).getAuthor())) {
            repository.printCommit(c);
        }
        /*
         * List<Commit> listOfCommits = repository.getCommits();
         *
         * for(Commit c : listOfCommits){ System.out.println(" -----");
         * System.out.println(c.getAbbreviateCommitHash());
         * System.out.println(c.getCommitHash());
         * System.out.println(c.getAuthorTime());
         * System.out.println(c.getCommitterTime());
         * System.out.println(c.getSubject()); System.out.println(c.getBody());
         * System.out.println(c.getAuthor().getName());
         * System.out.println(c.getAuthor().getEmail());
         * System.out.println(c.getCommitter().getName());
         * System.out.println(c.getCommitter().getEmail()); }
         */

    }

}
