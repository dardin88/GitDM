/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Hunk {

    private final GitRepository gitRepository;

    public Hunk(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public void calculateHunks(String pathFileOutput) {
        try (FileWriter fw = new FileWriter(pathFileOutput);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("id,bug,hunks");

            int i = 0;
            for (Commit commit : gitRepository.getCommits()) {
                System.out.println("Commit: " + i++);
                pw.println(commit.getCommitHash() + "," + commit.isBug() + "," + calculateHunk(commit));
            }

        } catch (IOException ex) {
            Logger.getLogger(Hunk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int calculateHunk(Commit commit) {
        int numOfHunks = 0;
        for (Change change : commit.getChanges()) {
            numOfHunks = numOfHunks + calculateHunk(change);
        }
        return numOfHunks;
    }

    private int calculateHunk(Change change) {
        int numOfHunks = 0;
        List<Integer> linesOfChange = new ArrayList<>();
        for (Integer lines : change.getAddedlines()) {
            if (!isInHunk(linesOfChange, lines)) {
                numOfHunks++;
            }
            linesOfChange.add(lines);
        }

        for (Integer lines : change.getRemovedlines()) {
            if (!isInHunk(linesOfChange, lines)) {
                numOfHunks++;
            }
            linesOfChange.add(lines);
        }

        for (Integer lines : change.getModifiedlines()) {
            if (!isInHunk(linesOfChange, lines)) {
                numOfHunks++;
            }
            linesOfChange.add(lines);
        }

        return numOfHunks;
    }

    private boolean isInHunk(List<Integer> linesOfChange, Integer newLine) {
        boolean found = false;
        for (Integer line : linesOfChange) {
            if (newLine == line - 1 || newLine == line + 1) {
                found = true;
            }
        }
        return found;
    }

}
