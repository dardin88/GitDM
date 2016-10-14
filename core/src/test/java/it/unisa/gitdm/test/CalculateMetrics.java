/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Metrics;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateMetrics {

    public static void main(String[] args) {

        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");
        GitRepository gitRepository = process.getGitRepository();

        // gitRepository.setDirectory(new
        // File("/home/sesa/Scrivania/Michele_Tufano/projects/tomcat70"));
        Metrics metrics = new Metrics(gitRepository);

        metrics.calculateMetricsLog("/home/sesa/Scrivania/metrics.csv");
    }

}
