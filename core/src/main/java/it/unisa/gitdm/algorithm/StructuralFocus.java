/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class StructuralFocus {

    private final GitRepository gitRepository;

    public StructuralFocus(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    static public int greatestCommonPrefixIndex(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return i;
            }
        }
        return 0;
    }

    public void calculateStructuralFocus(String pathOutputFile) {
        try (
                FileWriter fw = new FileWriter(pathOutputFile);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println("id,bug,structuralFocus");

            int i = 0;
            for (Commit commit : gitRepository.getCommits()) {
                System.out.println("Commit: " + i++);
                pw.println(commit.getCommitHash() + "," + commit.isBug() + ","
                        + calculateStructuralFocusInCommit(commit));
            }

        } catch (IOException ex) {
            Logger.getLogger(StructuralFocus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double calculateStructuralFocusInCommit(Commit commit) {
        int totalStructuralFocus = 0;
        int num = 0;

        // Single-File Commit
        if (commit.getChanges().size() == 1) {
            return 0;
        }

        for (Change change1 : commit.getChanges()) {
            FileBean f1 = change1.getFile();
            for (Change change2 : commit.getChanges()) {
                if (!change1.equals(change2)) {
                    FileBean f2 = change2.getFile();
                    totalStructuralFocus = totalStructuralFocus
                            + calculateStructuralFocusBetweenFiles(f1, f2);
                    num++;
                }
            }
        }

        double structuralFocus = 0;
        if (num > 0) {
            structuralFocus = (double) totalStructuralFocus / num;
        }

        return structuralFocus;
    }

    int calculateStructuralFocusBetweenFiles(FileBean f1, FileBean f2) {
        int structuralFocus = 0;

        String pathFile1 = f1.getPath();
        String pathFile2 = f2.getPath();

        int index = greatestCommonPrefixIndex(pathFile1, pathFile2);

        String differentPathFile1 = pathFile1.substring(index);
        String differentPathFile2 = pathFile2.substring(index);

        structuralFocus = structuralFocus
                + Metrics.numOfOccurrence(differentPathFile1, "/");
        structuralFocus = structuralFocus
                + Metrics.numOfOccurrence(differentPathFile2, "/");

        return structuralFocus;
    }

}
