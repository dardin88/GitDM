/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.newcomers.runner;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import it.unisa.gitdm.algorithm.Process;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabiopalomba
 */
public class CalculateDeveloperSurvival {

    public CalculateDeveloperSurvival(String projectName, String scatteringFolderPath) {

        String output = "author;days-in-project;abandoned\n";

        Calendar c = Calendar.getInstance();
        Process process = new it.unisa.gitdm.algorithm.Process();
        process.initGitRepositoryFromFile(scatteringFolderPath + "/" + projectName
                + "/gitRepository.data");

        List<Commit> commits = process.getGitRepository().getCommits();
        List<Author> authors = process.getGitRepository().getAuthorRepository().getAuthors();

        // TODO: define file path!
        File developerSurvivalFile = new File(scatteringFolderPath + projectName + "/developerSurvival.csv");

        try {
            PrintWriter pw1 = new PrintWriter(developerSurvivalFile);
            pw1.write("developer,number-of-days,abandoned\n");

            for (Author author : authors) {
                boolean abandoned = false;

                Commit firstCommitOfAuthor = this.getFirstCommitOf(author, commits);
                Commit lastCommitOfAuthor = this.getLastCommitOf(author, commits);

                long firstCommitTime = firstCommitOfAuthor.getAuthorTime();
                long lastCommitTime = lastCommitOfAuthor.getAuthorTime();

                c.setTimeInMillis(lastCommitTime);
                int month = c.get(Calendar.MONTH);

                int firstCommitInDays = (int) (firstCommitTime / (1000 * 60 * 60 * 24));
                int lastCommitInDays = (int) (lastCommitTime / (1000 * 60 * 60 * 24));

                if (month > 3) {
                    abandoned = true;
                }

                int numberOfDaysInProject = lastCommitInDays - firstCommitInDays;

                output += author.getEmail() + "," + numberOfDaysInProject + "," + abandoned + "\n";

                pw1.write(output);
                pw1.flush();

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalculateDeveloperSurvival.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Commit getLastCommitOf(Author pAuthor, List<Commit> pCommits) {
        Commit lastCommit = null;

        for (Commit commit : pCommits) {
            if (commit.getAuthor().equals(pAuthor)) {
                lastCommit = commit;
            }
        }

        return lastCommit;
    }

    private Commit getFirstCommitOf(Author pAuthor, List<Commit> pCommits) {

        for (Commit commit : pCommits) {
            if (commit.getAuthor().equals(pAuthor)) {
                return commit;
            }
        }

        return null;
    }
}
