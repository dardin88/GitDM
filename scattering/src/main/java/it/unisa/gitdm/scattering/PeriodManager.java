package it.unisa.gitdm.scattering;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.bean.Period;

import java.util.ArrayList;
import java.util.List;

public class PeriodManager {

    private static List<Period> periodList;

    public static List<Period> getList() {
        return periodList;
    }

    public static Period getPeriod(int id) {
        for (Period p : periodList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static void calculatePeriods(List<Commit> commits,
                                        String periodLengthString) {
        periodList = new ArrayList<>();
        int periodId = 1;
        long periodLength = 0;
        List<Change> changes = new ArrayList<>();
        List<Commit> periodCommits = new ArrayList<>();
        Commit firstCommit = commits.get(0);
        Commit previousCommit = commits.get(0);

        if (periodLengthString.equals("burst")) {
            periodLength = 172800l;
            for (Commit commit : commits) {
                if (commit.getAuthorTime() - previousCommit.getAuthorTime() > periodLength) {
                    // Sistemo il vecchio
                    Period p = new Period(periodId, changes, periodCommits);
                    periodList.add(p);

                    periodId++;

                    changes = new ArrayList<>();
                    changes.addAll(commit.getChanges());

                    periodCommits = new ArrayList<>();
                    periodCommits.add(commit);
                    previousCommit = commit;
                } else {
                    changes.addAll(commit.getChanges());
                    periodCommits.add(commit);
                    previousCommit = commit;
                }
            }
        } else if (periodLengthString.equals("All")) {

            Period p = new Period(periodId, changes, periodCommits);
            periodList.add(p);
                        
            for (Commit commit : commits) {
                changes.addAll(commit.getChanges());
                periodCommits.add(commit);
                previousCommit = commit;
                
            }
        } else {
            if (periodLengthString.equals("6m")) {
                periodLength = 15778800l;
            }
            if (periodLengthString.equals("3m")) {
                periodLength = 7889400l;
            }
            if (periodLengthString.equals("2m")) {
                periodLength = 5259600l;
            }
            if (periodLengthString.equals("1m")) {
                periodLength = 2629800l;
            }

            for (Commit commit : commits) {
                if (commit.getAuthorTime() - firstCommit.getAuthorTime() > periodLength) {
                    // Sistemo il vecchio
                    Period p = new Period(periodId, changes, periodCommits);
                    periodList.add(p);

                    periodId++;
                    firstCommit = commit;

                    changes = new ArrayList<>();
                    changes.addAll(commit.getChanges());

                    periodCommits = new ArrayList<>();
                    periodCommits.add(commit);
                } else {
                    changes.addAll(commit.getChanges());
                    periodCommits.add(commit);
                }
            }
        }

        if (periodCommits.size() > 0) {
            Period p = new Period(periodId, changes, periodCommits);
            periodList.add(p);
        }
    }

    public static int getPeriodFromCommit(Commit commit) {
        long commitTime = commit.getAuthorTime();
        for (Period p : periodList) {
            if (commitTime >= p.getCommits().get(0).getAuthorTime()
                    && commitTime <= p.getCommits()
                    .get(p.getCommits().size() - 1).getAuthorTime()) {
                return p.getId();
            }
        }
        return -1;
    }

    public static List<FileBean> getFiles(int id) {
        List<FileBean> files = new ArrayList<>();
        for (Period p : periodList) {
            if (p.getId() == id) {
                for (Change c : p.getChangedClasses()) {
                    if (!files.contains(c.getFile())
                            && c.getFile().getPath().contains(".java")) {
                        files.add(c.getFile());
                    }
                }
            }
        }
        return files;
    }
}
