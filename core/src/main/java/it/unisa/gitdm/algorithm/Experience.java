package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;
import java.util.ArrayList;
import java.util.HashMap;

public class Experience {

    private final static int LOW_EXPERIENCE = 0;
    private final static int MEDIUM_EXPERIENCE = 1;
    private final static int HIGH_EXPERIENCE = 2;

    private GitRepository gitRepository;

    public Experience(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public int calculateExperienceBasedOnNumberOfCommit(Author author, Commit commit) {
        int commitIndex = gitRepository.getCommits().indexOf(commit) + 1;

        HashMap<Author, Integer> commitsAuthor = new HashMap<Author, Integer>();
        for (int i = 0; i < commitIndex; i++) {
            Commit c = gitRepository.getCommits().get(i);
            Author a = c.getAuthor();
            Integer numberOfCommits = 0;

            if (commitsAuthor.containsKey(a)) {
                numberOfCommits = commitsAuthor.get(a);
                commitsAuthor.remove(a);
            }

            commitsAuthor.put(a, numberOfCommits + 1);
        }

        double currentAuthorValue = 0.0;

        if (commitsAuthor.get(author) != null) {
            currentAuthorValue = commitsAuthor.get(author);
            ArrayList<Double> values = new ArrayList<Double>();

            for (Integer i : commitsAuthor.values()) {
                double d = i;
                values.add(d);
            }

            //Ranked list authors
            double[] quartiles = BasicStatistics.Quartiles(values);

            //Il valore di experience in base alla posizione dell'autore nella classifica
            if (currentAuthorValue <= quartiles[0]) {
                return LOW_EXPERIENCE;
            } else if (currentAuthorValue >= quartiles[2]) {
                return HIGH_EXPERIENCE;
            } else {
                return MEDIUM_EXPERIENCE;
            }
        }
        
        return LOW_EXPERIENCE;
    }

    public int calculateExperience(String file, Author author, Commit commit) {
        int commitIndex = gitRepository.getCommits().indexOf(commit);

        //Quanto e quali sviluppatori hanno modificato quel file
        Ownership ownership = new Ownership(gitRepository);
        HashMap<Author, Integer> authorsOnFile = ownership.getAuthorsOnFile(file, commitIndex);

        double currentAuthorValue = authorsOnFile.get(author);

        ArrayList<Double> values = new ArrayList<Double>();

        for (Integer i : authorsOnFile.values()) {
            double d = i;
            values.add(d);
        }

        // Stampe
        String valuesString = "";
        for (double d : values) {
            valuesString += d + " ";
        }
        valuesString += " : " + currentAuthorValue;
        System.out.println(valuesString);

        //Ranked list authors
        double[] quartiles = BasicStatistics.Quartiles(values);

        //Il valore di experience in base alla posizione dell'autore nella classifica
        if (currentAuthorValue <= quartiles[0]) {
            return LOW_EXPERIENCE;
        } else if (currentAuthorValue >= quartiles[2]) {
            return HIGH_EXPERIENCE;
        } else {
            return MEDIUM_EXPERIENCE;
        }
    }

    public String getExperience(int experience) {
        if (experience == LOW_EXPERIENCE) {
            return "low";
        } else if (experience == MEDIUM_EXPERIENCE) {
            return "medium";
        } else {
            return "high";
        }
    }
}
