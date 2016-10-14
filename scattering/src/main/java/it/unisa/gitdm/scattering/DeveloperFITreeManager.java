package it.unisa.gitdm.scattering;

import it.unisa.gitdm.bean.*;

import java.util.ArrayList;
import java.util.List;

public class DeveloperFITreeManager {

    private static List<DeveloperTree> devFITrees;

    private static void addDeveloperTree(DeveloperTree devFITree) {
        devFITrees.add(devFITree);
    }

    private static DeveloperTree getDeveloperTree(Developer developer, int periodCounter) {
        for (DeveloperTree devTree : devFITrees) {
            if (devTree.getDeveloper().equals(developer)
                    && devTree.getPeriod() == periodCounter) {
                return devTree;
            }
        }
        return null;
    }

    public static List<DeveloperTree> getDeveloperTrees() {
        return devFITrees;
    }

    public static List<Developer> getDevelopersOnFile(FileBean f, int periodId) {
        List<Developer> authors = new ArrayList<>();
        for (DeveloperTree devTree : devFITrees) {
            if (devTree.getPeriod() == periodId) {
                for (ChangedClass c : devTree.getChangeSet().values()) {
                    if (c.getClassFile().getPath().equals(f.getPath())) {
                        authors.add(devTree.getDeveloper());
                    }
                }
            }
        }
        return authors;
    }

    public static int getNumberOfChanges(Developer developer, int periodId, FileBean f) {
        int numberOfChanges = 0;
        for (DeveloperTree devTree : devFITrees) {
            if (devTree.getDeveloper().equals(developer)) {
                if (devTree.getPeriod() == periodId) {
                    for (ChangedClass c : devTree.getChangeSet().values()) {
                        if (c.getClassFile().equals(f)) {
                            numberOfChanges++;
                        }
                    }
                }
            }
        }
        return numberOfChanges;
    }

    public static void calculateDeveloperTrees(List<Commit> commits,
                                               String periodLengthString) {
        devFITrees = new ArrayList<>();

        PeriodManager.calculatePeriods(commits, periodLengthString);
        for (Commit commit : commits) {
            if (commit.isFeatureIntroduction()) {
                Developer developer = commit.getAuthor();
                int period = PeriodManager.getPeriodFromCommit(commit);

                DeveloperTree devTree = getDeveloperTree(developer, period);

                if (devTree == null) {
                    addDeveloperTree(new DeveloperTree(developer, period));
                    devTree = getDeveloperTree(developer, period);
                }

                for (Change c : commit.getChanges()) {
                    if (c.getFile().getPath().contains(".java")) {
                        assert devTree != null;
                        devTree.addChange(c);
                    }
                }
            }

        }
    }
}
