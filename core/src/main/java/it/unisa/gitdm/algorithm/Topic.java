/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.source.Git;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Topic {

    private final GitRepository gitRepository;

    public Topic(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public void calculateTopic(String pathCommitBugDir, String pathCommitNonBugDir) {
        int numOfFile = 0;
        int index = 0;
        for (Commit commit : gitRepository.getCommits()) {
            System.out.println("Commit: " + index++);
            for (Change change : commit.getChanges()) {
                numOfFile++;
                String fileContent = Git.gitShow(gitRepository.getDirectory(),
                        change.getFile(), commit);
                String pathFileToCreate;
                if (commit.isBug()) {
                    pathFileToCreate = pathCommitBugDir + "file_" + numOfFile;
                } else {
                    pathFileToCreate = pathCommitNonBugDir + "file_" + numOfFile;
                }

                try (PrintWriter writer = new PrintWriter(pathFileToCreate)) {
                    writer.println(fileContent);
                    writer.flush();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Topic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
