/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.SZZ;
import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.File;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class LocateFixInducingChangesTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        GitRepository gitRepository = new GitRepository();

        //File directory = new File("/home/michele/AFNetworking/");
        File directory = new File("/home/michele/Repo GIT/");
        //File xx = new File("/home/michele/S");

        gitRepository.setDirectory(directory);

        gitRepository.initialize();

        System.out.println("Num of commits: " + gitRepository.getCommits().size());

        gitRepository.printAllCommits();

        Bug bug = new Bug();
        bug.setSubject("sdffdsg");
        bug.setID("123");
        bug.setStatus("RESOLVED");
        bug.setReportedTime(1380550400);

        SZZ.identifyBugFixWithStandardModel(bug, gitRepository.getCommits());

        for (Commit c : gitRepository.getCommits()) {
            if (c.isFix()) {
                System.out.println("\n TROVATO IL FIX!!");
                System.out.println("Eccolo: " + c.getSubject());
            }
        }

        SZZ.locateFixInducingChanges(bug, gitRepository);

        for (Commit c : gitRepository.getCommits()) {
            if (c.isBug()) {
                System.out.println("TROVATO IL BUG!!");
                System.out.println("Eccolo: " + c.getSubject());
            }
        }
    }

}
