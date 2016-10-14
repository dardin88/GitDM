package it.unisa.gitdm.metrics;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.Committer;

import java.util.ArrayList;
import java.util.List;

public class HistoricalMetrics {

    public static double averageCommitSize(String fileName, List<Commit> pHistory) {
        int commitSize = 0;
        int numberOfCommit = 0;

        for (Commit commit : pHistory) {
            for (Change resource : commit.getChanges()) {
                if (resource
                        .getFile()
                        .getPath()
                        .contains(
                                fileName.replaceAll("\\.", "/").replace(
                                        "/java", ".java"))) {
                    commitSize += commit.getChanges().size();
                    numberOfCommit++;
                }
            }
        }

        if (numberOfCommit > 0) {
            return commitSize / numberOfCommit;
        } else {
            return 0.0;
        }
    }

    public double numberOfChanges(String fileName, List<Commit> pHistory) {
        double changes = 0.0;

        for (Commit commit : pHistory) {
            for (Change resource : commit.getChanges()) {
                if (resource
                        .getFile()
                        .getPath()
                        .contains(
                                fileName.replaceAll("\\.", "/").replace(
                                        "/java", ".java"))) {
                    changes++;
                }
            }
        }

        return changes;
    }

    public double numberOfCommittors(String fileName, List<Commit> pHistory) {
        ArrayList<Committer> committors = new ArrayList<>();
        for (Commit commit : pHistory) {
            for (Change resource : commit.getChanges()) {
                if (resource.getFile().getPath().contains(fileName.replaceAll("\\.", "/").replace("/java", ".java"))) {
                    committors.add(commit.getCommitter());
                }
            }
        }
        return committors.size();
    }

    public double numberOfFix(String fileName, List<Commit> pHistory) {
        double numberOfFix = 0.0;

        for (Commit commit : pHistory) {
            for (Change resource : commit.getChanges()) {
                if (resource
                        .getFile()
                        .getPath()
                        .contains(
                                fileName.replaceAll("\\.", "/").replace(
                                        "/java", ".java"))) {
                    if (commit.getBody().toLowerCase().contains("fix")
                            || commit.getBody().toLowerCase().contains("bug")
                            || commit.getBody().toLowerCase()
                            .contains("resolved")
                            || commit.getBody().toLowerCase()
                            .contains("solved")
                            || commit.getBody().toLowerCase()
                            .contains("resolution")) {
                        numberOfFix++;
                    }

                }
            }
        }

        return numberOfFix;
    }
}
