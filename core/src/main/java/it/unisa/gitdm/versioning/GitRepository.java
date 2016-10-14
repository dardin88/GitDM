/**
 *
 */
package it.unisa.gitdm.versioning;

import it.unisa.gitdm.author.GitAuthorRepository;
import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.Committer;
import it.unisa.gitdm.source.Git;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public final class GitRepository implements CodeVersioningSystemRepository, Serializable {

    private File directory;
    private List<Commit> commits;
    private GitAuthorRepository authorRepository;

    public GitRepository() {
        commits = new ArrayList<>();
    }

    /**
     * Initializes repository Reads all commits from git-log and generate
     * instances of Commit Class
     *
     * @return true if initializes is successful, false otherwise
     */
    @Override
    public boolean initialize() {
        if (directory != null) {
            commits = Git.extractCommit(directory, true);
            authorRepository = new GitAuthorRepository();
            authorRepository.init(this);
            return true;
        }
        return false;
    }

    /**
     * Save the Git Repository
     *
     * @param path Path of file where save the repository
     */
    public void save(String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            BufferedOutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(this);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(GitRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Serialized data is saved in " + path);
    }

    /**
     * Find commit by ID
     *
     * @param ID : commit hash or abbreviate commit has
     * @return commit instance or null value if not found
     */
    @Override
    public Commit getCommitByID(String ID, boolean isSVN) {

        if (isSVN) {
            String[] cmd = new String[]{"git", "svn", "find-rev", "r" + ID};
            String line;
            String log = "";
            try {
                Process pr = Runtime.getRuntime().exec(cmd, null, this.directory.getAbsoluteFile());
                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                while ((line = in.readLine()) != null) {
                    log = line;
                }
                for (Commit c : commits) {
                    if (c.getCommitHash().equalsIgnoreCase(log)
                            || c.getAbbreviateCommitHash().equalsIgnoreCase(log)) {
                        System.out.println("Commit:" + c);
                        return c;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(GitRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (Commit c : commits) {
                if (c.getCommitHash().equalsIgnoreCase(ID)
                        || c.getAbbreviateCommitHash().equalsIgnoreCase(ID)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Find all commits edited by a specified author
     *
     * @param author : author of commits
     * @return list of all commits edited by a specified author
     */
    @Override
    public List<Commit> getCommitsByAuthor(Author author) {
        List<Commit> listOfCommits = new ArrayList<>();

        for (Commit c : commits) {
            if (c.getAuthor().equals(author)) {
                listOfCommits.add(c);
            }
        }
        return listOfCommits;
    }

    /**
     * Find all commits committed by a specified committer
     *
     * @param committer : committer of commits
     * @return list of all commits committed by a specified committer
     */
    @Override
    public List<Commit> getCommitsByCommitter(Committer committer) {
        List<Commit> listOfCommits = new ArrayList<>();

        for (Commit c : commits) {
            if (c.getCommitter().equals(committer)) {
                listOfCommits.add(c);
            }
        }
        return listOfCommits;
    }

    /**
     * Prints all commits passed
     *
     * @param commits : List of commits to print
     */
    public void printCommits(List<Commit> commits) {
        for (Commit c : commits) {
            System.out.println("\n---- Commit ----");
            printCommit(c);
        }
    }

    /**
     * Prints all commits in repository on standard output
     */
    public void printAllCommits() {
        for (Commit c : commits) {
            System.out.println("\n---- Commit ----");
            printCommit(c);
        }
    }

    /**
     * Prints a specified commit in repository on standard output
     *
     * @param commit : commit to print
     */
    public void printCommit(Commit commit) {
        System.out.println("Abbreviate Commit Hash: "
                + commit.getAbbreviateCommitHash());
        System.out.println("Commit Hash: " + commit.getCommitHash());
        System.out.println("Author Time: " + commit.getAuthorTime());
        System.out.println("Committer Time: " + commit.getCommitterTime());
        System.out.println("Subject: " + commit.getSubject());
        System.out.println("Body: " + commit.getBody());
        System.out.println("Author Name: " + commit.getAuthor().getName());
        System.out.println("Author Email: " + commit.getAuthor().getEmail());
        System.out
                .println("Committer Name: " + commit.getCommitter().getName());
        System.out
                .println("Committer Email" + commit.getCommitter().getEmail());

        System.out.println("\n---- Changes ----");
        List<Change> changes = commit.getChanges();
        for (Change change : changes) {
            System.out.println("\n\nFile: " + change.getFile().getPath());
            System.out.print("Added Lines: ");
            for (Integer l : change.getAddedlines()) {
                System.out.print(l + " ");
            }
            System.out.print("\nRemoved Lines: ");
            for (Integer l : change.getRemovedlines()) {
                System.out.print(l + " ");
            }
            System.out.print("\nModified Lines: ");
            for (Integer l : change.getModifiedlines()) {
                System.out.print(l + " ");
            }
        }
    }

    public void getSnapshot(Commit commit, File workingDirectory) {
        Git.gitCheckout(directory, commit, workingDirectory);
    }

    /**
     * Returns the directory of repository
     *
     * @return File rapresent the directory of repository
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Sets the directory of repository
     *
     * @param directory : File rapresent the directory of repository
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * Return a list of commits of repository
     *
     * @return List of commits of repository
     */
    public List<Commit> getCommits() {
        return commits;
    }

    /**
     * Sets a list of commits of repository
     *
     * @param commits : List of commits of repository
     */
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public GitAuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public void setAuthorResository(GitAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
