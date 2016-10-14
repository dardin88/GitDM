package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Commit;

public class CommitGoalTagger {

    public static boolean isFeatureIntroduction(Commit pCommit) {
        return CommitGoalTagger.isEnhancement(pCommit) || CommitGoalTagger.isNewFeature(pCommit);
    }

    public static boolean isEnhancement(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("updat")) || (commitMessage.toLowerCase().contains("modif"))
                || (commitMessage.toLowerCase().contains("upgrad")) || (commitMessage.toLowerCase().contains("export"))
                || (commitMessage.toLowerCase().contains("remov")) || (commitMessage.toLowerCase().contains("integrat"))
                || (commitMessage.toLowerCase().contains("support")) || (commitMessage.toLowerCase().contains("enhancement"))
                || (commitMessage.toLowerCase().contains("replac")) || (commitMessage.toLowerCase().contains("includ"))
                || (commitMessage.toLowerCase().contains("expos")) || (commitMessage.toLowerCase().contains("better"))
                || (commitMessage.toLowerCase().contains("svn")) || (commitMessage.toLowerCase().contains("generate"));
    }

    public static boolean isNewFeature(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("new")) || (commitMessage.toLowerCase().contains("feature"))
                || (commitMessage.toLowerCase().contains("add")) || (commitMessage.toLowerCase().contains("create"))
                || (commitMessage.toLowerCase().contains("introduc")) || (commitMessage.toLowerCase().contains("migrat"));
    }

    public static boolean isBugFixing(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("fix")) || (commitMessage.toLowerCase().contains("repair"))
                || (commitMessage.toLowerCase().contains("error")) || (commitMessage.toLowerCase().contains("avoid"))
                || (commitMessage.toLowerCase().contains("can ")) || (commitMessage.toLowerCase().contains("bug "))
                || (commitMessage.toLowerCase().contains("issue ")) || (commitMessage.toLowerCase().contains("#"))
                || (commitMessage.toLowerCase().contains("exception"));
    }

    public static boolean isRefactoring(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("renam")) || (commitMessage.toLowerCase().contains("reorganiz"))
                || (commitMessage.toLowerCase().contains("refactor")) || (commitMessage.toLowerCase().contains("clean"))
                || (commitMessage.toLowerCase().contains("polish")) || (commitMessage.toLowerCase().contains("typo"))
                || (commitMessage.toLowerCase().contains("move")) || (commitMessage.toLowerCase().contains("extract"))
                || (commitMessage.toLowerCase().contains("reorder")) || (commitMessage.toLowerCase().contains("re-order"));
    }

    public static boolean isMerge(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("merge"));
    }

    public static boolean isPorting(Commit pCommit) {
        String commitMessage = pCommit.getSubject() + " " + pCommit.getBody();

        return (commitMessage.toLowerCase().contains("initial "));
    }
}
