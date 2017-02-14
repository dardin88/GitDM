package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dardin88
 */
public class ProjectStatistics {

    public static int getNumberOfEnhancements(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int enhancement = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Commit c : fileMap.getValue()) {
                    //System.out.println("File: " +filePath + "FileMap: " + fileMap.getKey() + "Commit: "+c);
                    if (p.getCommits().contains(c) && CommitGoalTagger.isEnhancement(c)) {
                        enhancement++;
                    }

                }
            }
        }
        return enhancement;
    }

    public static int getNumberOfNewFeatures(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int newFeatures = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isNewFeature(c)) {
                        newFeatures++;
                    }
                }
            }
        }
        return newFeatures;
    }

    public static int getNumberOfBugFixing(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int bugFixing = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isBugFixing(c)) {
                        bugFixing++;
                    }
                }
            }
        }
        return bugFixing;
    }

    public static int getNumberOfRefactoring(String filePath, Process process, Period p) {

        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int refactoring = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c) && CommitGoalTagger.isRefactoring(c)) {
                        refactoring++;
                    }
                }
            }
        }
        return refactoring;
    }

    public static String getOwner(String filePath, Process process) {
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        String own = null;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                HashMap<Author, Integer> authors = ownership.calculateAuthorsOnFile(fileMap);
                Author owner = ownership.findOwner(authors, 40);
                if (owner != null) {
                    own = owner.getEmail();
                }
            }
        }
        return own;
    }

    public static int getNumberOfPastFault(String filePath, Process process, Period p, List<Period> periods) {
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        int pastFault = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                for (Period per : periods) {
                    if (per.getId() < p.getId()) {
                        //System.out.println("Periodo " + per.getId() + "\nCommit del Periodo: " + per.getCommits().size());
                        for (Commit c : per.getCommits()) {
                            if (fileMap.getValue().contains(c) && CommitGoalTagger.isBugFixing(c)) {
                                //System.out.println("is Bug!");
                                pastFault++;
                            }
                        }
                    }

                }
            }
        }
        return pastFault;
    }

    public static double getPosnett(String filePath, Process process, Period p) {
        //System.out.println("FILE:"+filePath);
        Ownership ownership = new Ownership(process.getGitRepository());
        HashMap<String, List<Commit>> files = ownership.getFiles();
        double totalCommit = 0;
        double MAF = 0;
        for (Map.Entry<String, List<Commit>> fileMap : files.entrySet()) {
            if (fileMap.getKey().equals(filePath)) {
                HashMap<Author, Integer> authors = ownership.calculateAuthorsOnFile(fileMap);
                for (Commit c : fileMap.getValue()) {
                    if (p.getCommits().contains(c)) {
                        totalCommit++;
                    }
                }
                double firstExpr = 0;
                double secondExpr = 0;
                for (Author a : authors.keySet()) {
                    double authorCommit = 0;
                    for (Commit c : fileMap.getValue()) {
                        if (p.getCommits().contains(c)) {
                            if (c.getAuthor().equals(a)) {
                                authorCommit++;
                            }
                        }
                    }
                    if (authorCommit == 0 || p.getCommits(a).isEmpty()) {
                        continue;
                    }
                    //System.out.println("Author:"+a+"\nAuthorCommit:"+authorCommit+"\ntotalCommit:"+totalCommit);
                    double wij = authorCommit / totalCommit;
                    //System.out.println("wij: "+wij);
                    if (Double.isNaN(wij)) {
                        wij = 0;
                    }
                    double commitsAuthorSize = p.getCommits(a).size();
                    double commitSize = p.getCommits().size();
                    //System.out.println("Dj:"+commitsAuthorSize +"\nA:"+ commitSize);
                    //System.out.println("ln: " + Math.log(commitsAuthorSize/commitSize));
                    double ln = Math.log(commitsAuthorSize / commitSize);
                    firstExpr = firstExpr + (wij * ln);
                    secondExpr = secondExpr + (wij * Math.log(wij));

                }
                MAF = (-firstExpr) - (-secondExpr);
                //System.out.println("MAF:"+ MAF);    
            }
        }
        return MAF;
    }
}
