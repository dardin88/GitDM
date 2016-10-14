/**
 *
 */
package it.unisa.gitdm.versioning;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.Committer;

import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public interface CodeVersioningSystemRepository {

    /**
     * Initializes repository Reads all commits from Code Versioning System and
     * generate instances of Commit Class
     *
     * @return true if initializes is successful, false otherwise
     */
    boolean initialize();

    /**
     * Find commit by ID
     *
     * @param ID : commit hash or abbreviate commit has
     * @return commit instance or null value if not found
     */
    Commit getCommitByID(String ID, boolean isSVN);

    /**
     * Find all commits edited by a specified author
     *
     * @param author : author of commits
     * @return list of all commits edited by a specified author
     */
    List<Commit> getCommitsByAuthor(Author author);

    /**
     * Find all commits committed by a specified committer
     *
     * @param committer : committer of commits
     * @return list of all commits committed by a specified committer
     */
    List<Commit> getCommitsByCommitter(Committer committer);

}
