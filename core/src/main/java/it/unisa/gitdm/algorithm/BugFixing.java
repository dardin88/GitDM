/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class BugFixing {

    private final GitRepository gitRepository;

    public BugFixing(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public void identifyWhoFixBug() {
        int sameAuthor = 0;
        int differentAuthor = 0;
        for (Commit commit : gitRepository.getCommits()) {
            if (commit.isBug()) {
                if (commit.getAuthor().getEmail().equalsIgnoreCase(commit.getIntroducedBug().getFix().getAuthor().getEmail())) {
                    sameAuthor++;
                } else {
                    differentAuthor++;
                }
            }
        }

        System.out.println("Same Author: " + sameAuthor);
        System.out.println("Different Author: " + differentAuthor);
    }
}
