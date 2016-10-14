/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Interferences;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateInterferences {

    public static void main(String[] args) {
        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");
        GitRepository gitRepository = process.getGitRepository();

        Interferences interferences = new Interferences(gitRepository);

        interferences.calculate("/home/sesa/Scrivania/interferences.csv");

    }
}
