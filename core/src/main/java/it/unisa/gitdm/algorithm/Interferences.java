/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Interferences {

    private final GitRepository gitRepository;

    public Interferences(GitRepository repository) {
        this.gitRepository = repository;
    }

    private static List<FileBean> findBugFiles(Commit currentCommit) {
        Bug inductedBug = currentCommit.getIntroducedBug();
        Commit commitFix = inductedBug.getFix();

        List<FileBean> bugFiles = new ArrayList<>();

        //Scan the change of commitFix but I read the file at the moment of currentCommit (when the bug was introduced)
        for (Change changeFix : commitFix.getChanges()) {
            for (Change changeCommit : currentCommit.getChanges()) {
                //Only the files involved in the fix and in the commit
                if (changeCommit.getFile().getPath().equalsIgnoreCase(changeFix.getFile().getPath())) {
                    //Read all source code of files touched in the current commit but ONLY for the files touched by the fix
                    bugFiles.add(changeCommit.getFile());
                }
            }
        }

        return bugFiles;
    }

    public void calculate(String pathOutputFile) {
        try (FileWriter fw = new FileWriter(pathOutputFile);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("Commit, Author, isBug, Interferences, add-rem-mod Lines, Interferences for Bug, add-rem-mod Lines for Bug");

            int totalInterferences = 0;
            int totalInterferencesForBugAllFile = 0;
            int totalInterferencesForBug = 0;

            int totalAddRemModLines = 0;
            int totalAddRemModLinesForBugAllFile = 0;
            int totalAddRemModLinesForBug = 0;

            int numTotalCommit = 0;
            int numTotalBugCommit = 0;

            System.out.println("Start calculate interferences...");
            for (Commit commit : gitRepository.getCommits()) {

                numTotalCommit++;
                List<Integer> values = calculateInterferences(commit, false);
                int interferences = values.get(0);
                int addRemModLines = values.get(1);
                totalInterferences += interferences;
                totalAddRemModLines += addRemModLines;
                int interferencesForBug = 0;
                int addRemModLinesForBug = 0;
                if (commit.isBug()) {
                    numTotalBugCommit++;
                    totalInterferencesForBugAllFile += interferences;
                    totalAddRemModLinesForBugAllFile += addRemModLines;
                    List<Integer> valuesBug = calculateInterferences(commit, true);
                    interferencesForBug = valuesBug.get(0);
                    addRemModLinesForBug = valuesBug.get(1);
                    totalInterferencesForBug += interferencesForBug;
                    totalAddRemModLinesForBug += addRemModLinesForBug;
                }

                pw.println(commit.getCommitHash() + "," + commit.getAuthor().getEmail() + "," + commit.isBug() + "," + interferences + "," + addRemModLines + "," + interferencesForBug + "," + addRemModLinesForBug);

            }
            System.out.println("End calculate");

            pw.println();

            double averageTotalInterferences = (double) totalInterferences / numTotalCommit;
            double averageTotalInterferencesForBugAllFile = (double) totalInterferencesForBugAllFile / numTotalBugCommit;
            double averageTotalInterferencesForBug = (double) totalInterferencesForBug / numTotalBugCommit;
            double averageTotalAddRemModLines = (double) totalAddRemModLines / numTotalCommit;
            double averageTotalAddRemModLinesForBugAllFile = (double) totalAddRemModLinesForBugAllFile / numTotalBugCommit;
            double averageTotalAddRemModLinesForBug = (double) totalAddRemModLinesForBug / numTotalBugCommit;
            double averageTotalAddRemModLinesInterferences = (double) totalAddRemModLines / totalInterferences;
            double averageTotalAddRemModLinesForBugAllFileInterferences = (double) totalAddRemModLinesForBugAllFile / totalInterferencesForBugAllFile;
            double averageTotalAddRemModLinesForBugInterferences = (double) totalAddRemModLinesForBug / totalInterferencesForBug;

            System.out.println("NumTotalCommit: " + numTotalCommit);
            System.out.println("NumBugCommit: " + numTotalBugCommit);
            System.out.println("Total Interference: " + totalInterferences);
            System.out.println("Total Interference Bug All File: " + totalInterferencesForBugAllFile);
            System.out.println("Total Interference Bug: " + totalInterferencesForBug);
            System.out.println("Average Total: " + averageTotalInterferences);
            System.out.println("Average Bug All File: " + averageTotalInterferencesForBugAllFile);
            System.out.println("Average Total Bug: " + averageTotalInterferencesForBug);
            System.out.println("Total add-rem-mod lines: " + totalAddRemModLines);
            System.out.println("Total add-rem-mod lines for Bug All File: " + totalAddRemModLinesForBugAllFile);
            System.out.println("Total add-rem-mod lines for Bug: " + totalAddRemModLinesForBug);
            System.out.println("Average add-rem-mod lines lines/commit: " + averageTotalAddRemModLines);
            System.out.println("Average add-rem-mod lines for Bug All File lines/commit: " + averageTotalAddRemModLinesForBugAllFile);
            System.out.println("Average add-rem-mod lines for Bug lines/commit: " + averageTotalAddRemModLinesForBug);
            System.out.println("Average add-rem-mod lines lines/interferences: " + averageTotalAddRemModLinesInterferences);
            System.out.println("Average add-rem-mod lines for Bug All File lines/interferences: " + averageTotalAddRemModLinesForBugAllFileInterferences);
            System.out.println("Average add-rem-mod lines for Bug lines/interferences: " + averageTotalAddRemModLinesForBugInterferences);

            pw.println("NumTotalCommit, " + numTotalCommit);
            pw.println("NumBugCommit, " + numTotalBugCommit);
            pw.println();

            pw.println("Total Interferences," + totalInterferences + ", Average," + averageTotalInterferences);
            pw.println("Total Interferences For Bug All File," + totalInterferencesForBugAllFile + ", Average," + averageTotalInterferencesForBugAllFile);
            pw.println("Total Interferences For Bug," + totalInterferencesForBug + ", Average," + averageTotalInterferencesForBug);
            pw.println();

            pw.println("Total add-rem-mod lines, " + totalAddRemModLines + ", Average lines/commit," + averageTotalAddRemModLines);
            pw.println("Total add-rem-mod lines for Bug All File, " + totalAddRemModLinesForBugAllFile + ", Average lines/commit," + averageTotalAddRemModLinesForBugAllFile);
            pw.println("Total add-rem-mod lines for Bug, " + totalAddRemModLinesForBug + ", Average lines/commit," + averageTotalAddRemModLinesForBug);
            pw.println();

            pw.println("Total add-rem-mod lines, " + totalAddRemModLines + ", Average lines/interferences," + averageTotalAddRemModLinesInterferences);
            pw.println("Total add-rem-mod lines for Bug All File, " + totalAddRemModLinesForBugAllFile + ", Average lines/interferences," + averageTotalAddRemModLinesForBugAllFileInterferences);
            pw.println("Total add-rem-mod lines for Bug, " + totalAddRemModLinesForBug + ", Average lines/interferences," + averageTotalAddRemModLinesForBugInterferences);

            System.out.println("Finish");
        } catch (IOException ex) {
            Logger.getLogger(Interferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Integer> calculateInterferences(Commit commit, boolean isBug) {
        int interferences = 0;
        int addRemModLines = 0;
        List<Integer> values;
        if (isBug) {
            List<FileBean> bugFiles = findBugFiles(commit);
            for (FileBean file : bugFiles) {
                Commit previousCommit = searchPreviousCommit(commit, file);
                if (previousCommit != null) {
                    values = calculateLocalInterferences(commit, previousCommit, file);
                    interferences += values.get(0);
                    addRemModLines += values.get(1);
                }
            }
        } else {
            for (Change change : commit.getChanges()) {
                FileBean file = change.getFile();
                Commit previousCommit = searchPreviousCommit(commit, file);
                if (previousCommit != null) {
                    values = calculateLocalInterferences(commit, previousCommit, file);
                    interferences += values.get(0);
                    addRemModLines += values.get(1);
                }
            }
        }

        List<Integer> valuesToReturn = new ArrayList<>();
        valuesToReturn.add(interferences);
        valuesToReturn.add(addRemModLines);
        return valuesToReturn;
    }

    //Trova il commit precedente dello stesso autore su quel file
    private Commit searchPreviousCommit(Commit currentCommit, FileBean file) {

        //Trovo tutti i commit precedenti dell'autore
        List<Commit> previousCommits = new ArrayList<>();
        for (Commit commit : gitRepository.getCommitsByAuthor(currentCommit.getAuthor())) {
            if (commit.getAuthorTime() < currentCommit.getAuthorTime()) {
                previousCommits.add(commit);
            }
        }

        ListIterator<Commit> iterator = previousCommits.listIterator(previousCommits.size());

        //Cerco in ordine inverso se c'è un commit sullo stesso file
        while (iterator.hasPrevious()) {
            Commit previousCommit = iterator.previous();
            for (Change change : previousCommit.getChanges()) {
                if (change.getFile().getPath().equalsIgnoreCase(file.getPath())) {
                    return previousCommit;
                }
            }
        }

        return null;
    }

    //Trova tutti i commit di altri autori che sono intervenuti su quel file tra un currentCommit e un previousCommit
    private List<Integer> calculateLocalInterferences(Commit currentCommit, Commit previousCommit, FileBean file) {

        int interferences = 0;
        int addRemModLines = 0;
        for (Commit commit : gitRepository.getCommits()) {
            //Intervallo temporale
            if (commit.getAuthorTime() > previousCommit.getAuthorTime() && commit.getAuthorTime() < currentCommit.getAuthorTime()) {
                //Autore diverso
                if (commit.getAuthor() != currentCommit.getAuthor()) {
                    for (Change change : commit.getChanges()) {
                        //Stesso file
                        if (change.getFile().getPath().equalsIgnoreCase(file.getPath())) {
                            interferences++;
                            addRemModLines += change.getAddedlines().size() + change.getRemovedlines().size() + change.getModifiedlines().size();
                        }
                    }
                }
            }
        }

        List<Integer> values = new ArrayList<>();
        values.add(interferences);
        values.add(addRemModLines);
        return values;
    }
}
