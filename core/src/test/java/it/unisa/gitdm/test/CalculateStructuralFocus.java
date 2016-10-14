/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.algorithm.StructuralFocus;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateStructuralFocus {

    public static void main(String[] args) {
        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");
        GitRepository gitRepository = process.getGitRepository();

        StructuralFocus structuralFocus = new StructuralFocus(gitRepository);

        structuralFocus.calculateStructuralFocus("/home/sesa/Scrivania/structuralFocus.csv");

    }
}
