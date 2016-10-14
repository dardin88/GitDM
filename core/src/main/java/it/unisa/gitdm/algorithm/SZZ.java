/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.metrics.semanticMetric.CosineSimilarity;
import it.unisa.gitdm.source.Git;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class SZZ {

    public static List<Commit> locateFixInducingChanges(Bug bug, GitRepository repository) {
        List<Commit> fixInducingChanges = new ArrayList<>();
        Commit fix = bug.getFix();
        System.out.println("Bug:" + bug.getID());
        if (fix == null) {
            return fixInducingChanges;
        }
        List<Change> changes = fix.getChanges();
        System.out.println("Number of Changes: " + changes.size());

        for (Change change : changes) {
            FileBean file = change.getFile();

            //Which last commit changed the modified lines of fix-commit
            for (Integer line : change.getModifiedlines()) {

                String candidateID = Git.gitBlame(repository.getDirectory(), fix, line, file.getPath());
                System.out.println("Candidate ID: " + candidateID);
                Commit candidate = repository.getCommitByID(candidateID, false);
                if (candidate != null) {
                    if (candidate.getCommitterTime() < bug.getReportedTime()) {
                        if (!fixInducingChanges.contains(candidate)) {
                            fixInducingChanges.add(candidate);
                            candidate.setBug(true);
                            candidate.setIntroducedBug(bug);
                        }
                    }
                }
            }

            //Which last commit changed the removed lines of fix-commit
            for (Integer line : change.getRemovedlines()) {
                String candidateID = Git.gitBlame(repository.getDirectory(), fix, line, file.getPath());
                Commit candidate = repository.getCommitByID(candidateID, false);
                if (candidate != null) {
                    if (candidate.getCommitterTime() < bug.getReportedTime()) {
                        if (!fixInducingChanges.contains(candidate)) {
                            fixInducingChanges.add(candidate);
                            candidate.setBug(true);
                            candidate.setIntroducedBug(bug);
                        }
                    }
                }
            }

            //The first previous commit that touched the fix files
            if (fixInducingChanges.isEmpty()) {
                Commit fixInducingCommit;
                int commitCounter = 0;
                int fixCommitCounter = 0;
                for (Commit candidate : repository.getCommits()) {
                    if (candidate.getChanges().contains(change)) {
                        System.out.println("Dentro if");
                        fixCommitCounter = commitCounter;
                    }
                    commitCounter++;
                }
                System.out.println("respository size: " + repository.getCommits().size() + " fixCommitCounter: " + fixCommitCounter + " commitCounter:" + commitCounter);
                if (fixCommitCounter != 0) {
                    fixInducingCommit = repository.getCommits().get(fixCommitCounter - 2);
                    if (fixInducingCommit != null) {
                        fixInducingChanges.add(fixInducingCommit);
                        fixInducingCommit.setBug(true);
                        fixInducingCommit.setIntroducedBug(bug);
                    }
                }
            }
        }

        return fixInducingChanges;
    }

    public static Commit identifyBugFixWithDiceIndex(Bug bug, List<Commit> commits) {
        Map<Commit, Double> similarities = new HashMap<>();

        for (Commit commit : commits) {

            List<String> commitWords = SZZ.mergeSubjectAndBody(commit.getSubject().split(" "), commit.getBody().split(" "));
            List<String> bugWords = Arrays.asList(bug.getSubject().split(" "));

            // (overlap) / min(bugStringSize, commitStringSize)
            int overlap = 0;

            for (String commitWord : commitWords) {
                for (String bugWord : bugWords) {
                    if (commitWord.equals(bugWord)) {
                        overlap++;
                    }
                }
            }

            double dice;

            if (commitWords.size() >= bugWords.size()) {
                if (commitWords.size() > 0) {
                    dice = overlap / ((float) commitWords.size());
                } else {
                    dice = 0.0;
                }
            } else {
                if (bugWords.size() > 0) {
                    dice = overlap / ((float) bugWords.size());
                } else {
                    dice = 0.0;
                }
            }

            similarities.put(commit, dice);
        }

        similarities = SZZ.sortByValues(similarities);

        Commit mostSimilarCommit = (Commit) similarities.keySet().toArray()[0];
        for (Commit c : commits) {
            if (c.getCommitHash().equals(mostSimilarCommit.getCommitHash())) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(mostSimilarCommit);
                return c;
            }
        }

        return null;
    }

    private static List<String> mergeSubjectAndBody(String[] pSubjectWords, String[] pBodyWords) {
        List<String> words = new ArrayList<>();

        words.addAll(Arrays.asList(pSubjectWords));

        words.addAll(Arrays.asList(pBodyWords));

        return words;
    }

    //Could be removed
    public static Commit identifyBugFixWithVSM(Bug bug, List<Commit> commits) {
        String[] bugSpecification = new String[2];
        bugSpecification[0] = bug.getID();
        bugSpecification[1] = bug.getSubject() + " " + bug.getBody();

        Map<Commit, Double> similarities = new TreeMap<>();

        for (Commit c : commits) {
            CosineSimilarity cosineSimilarity = new CosineSimilarity();

            String[] commitSpecification = new String[2];
            commitSpecification[0] = c.getAbbreviateCommitHash();
            commitSpecification[1] = c.getSubject() + " " + c.getBody();

            try {
                similarities.put(c, cosineSimilarity.computeSimilarity(bugSpecification, commitSpecification));
            } catch (IOException e) {
                similarities.put(c, 0.0);
            }
        }

        similarities = SZZ.sortByValues(similarities);

        for (Entry<Commit, Double> entry : similarities.entrySet()) {
            if (entry.getValue() > 0.65) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static Commit identifyBugFixWithStandardModel(Bug bug, List<Commit> commits) {
        String BugID = bug.getID();
        String EXPR1 = "fix(e[ds])?[ \t]*(for)[ ]*?(bugs?)?(defects?)?(pr)?[# \t]*" + BugID;
        String EXPR1_1 = "patch(ed)?[ \t]*(for)[ ]*?(bugs?)?(defects?)?(pr)?[# \t]*" + BugID;
        String EXPR2 = "(bugs?|pr|show_bug\\.cgi\\?id=)[# \t]*" + BugID;
        String EXPR3 = "#[ \t]*" + BugID;
        String EXPR4 = " " + BugID + " ";
        String EXPR5 = " " + BugID;

        //First and most significant evidence of bug fixing (EXPR1 - EXPR_1_1)
        for (Commit c : commits) {
            Pattern pattern = Pattern.compile(EXPR1);
            Matcher matcher = pattern.matcher(c.getSubject().toLowerCase());
            Matcher matcher2 = pattern.matcher(c.getBody().toLowerCase());
            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }
            pattern = Pattern.compile(EXPR1_1);
            matcher = pattern.matcher(c.getSubject().toLowerCase());
            matcher2 = pattern.matcher(c.getBody().toLowerCase());
            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }
        }

        //Find weak evidence (EXPR2)
        for (Commit c : commits) {
            Pattern pattern = Pattern.compile(EXPR2);
            Matcher matcher = pattern.matcher(c.getSubject().toLowerCase());
            Matcher matcher2 = pattern.matcher(c.getBody().toLowerCase());
            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }
        }

        //Find only BugID (EXPR3 - EXPR4)
        for (Commit c : commits) {
            Pattern pattern = Pattern.compile(EXPR3);
            Matcher matcher = pattern.matcher(c.getSubject().toLowerCase());
            Matcher matcher2 = pattern.matcher(c.getBody().toLowerCase());

            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }

            pattern = Pattern.compile(EXPR4);
            matcher = pattern.matcher(c.getSubject().toLowerCase());
            matcher2 = pattern.matcher(c.getBody().toLowerCase());

            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }

            pattern = Pattern.compile(EXPR5);
            matcher = pattern.matcher(c.getSubject().toLowerCase());
            matcher2 = pattern.matcher(c.getBody().toLowerCase());

            if (matcher.find() || matcher2.find()) {
                c.setFix(true);
                c.setFixedBug(bug);
                bug.setFix(c);
                return c;
            }
        }

        //Not found
        return null;
    }

    private static Map<Commit, Double> sortByValues(Map<Commit, Double> map) {
        CommitComparator comparator = new CommitComparator(map);

        Map<Commit, Double> sortedByValues = new TreeMap<>(comparator);
        sortedByValues.putAll(map);

        return sortedByValues;
    }

}

class CommitComparator implements Comparator<Commit> {

    private final Map<Commit, Double> map;

    public CommitComparator(Map<Commit, Double> map) {
        this.map = map;
    }

    @Override
    public int compare(Commit c1, Commit c2) {
        if (map.get(c1) >= map.get(c2)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
