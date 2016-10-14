/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Ownership;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class OwnershipFiltering {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");

        // Change Directory
        // process.getGitRepository().setDirectory(new
        // File("/home/sesa/Scrivania/Michele_Tufano/projects/tomcat70"));
        GitRepository gitRepository = process.getGitRepository();

        Ownership ownership = new Ownership(gitRepository);

        ownership.filterStats("/home/sesa/Scrivania/stats/statistics_", 47,
                "/home/sesa/Scrivania/filtered.csv", 75);
    }

}
