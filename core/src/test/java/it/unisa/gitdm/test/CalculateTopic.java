/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.algorithm.Topic;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateTopic {

    public static void main(String[] args) {
        Process process = new Process();
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");
        GitRepository gitRepository = process.getGitRepository();

        Topic topic = new Topic(gitRepository);

        String pathCommitBugDir = "/home/sesa/Scrivania/fileBugCommit/";
        String pathCommitNonBugDir = "/home/sesa/Scrivania/fileNonBugCommit/";

        topic.calculateTopic(pathCommitBugDir, pathCommitNonBugDir);

    }
}
