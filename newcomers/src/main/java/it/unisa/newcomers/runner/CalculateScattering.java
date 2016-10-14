package it.unisa.newcomers.runner;

import it.unisa.gitdm.algorithm.Experience;
import it.unisa.gitdm.algorithm.Newcomer;
import it.unisa.gitdm.algorithm.Process;
import it.unisa.gitdm.author.GitAuthorRepository;
import it.unisa.gitdm.bean.*;
import it.unisa.gitdm.scattering.DeveloperTreeManager;
import it.unisa.gitdm.scattering.PeriodManager;
import it.unisa.gitdm.scattering.ScatteringMetricsParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class CalculateScattering {

    public CalculateScattering(String projectName, String productName, String periodLength,
            String scatteringFolderPath) {

        Process process = new Process();

        process.initGitRepositoryFromFile(scatteringFolderPath + "/" + projectName
                + "/gitRepository.data");

        // Get authors list (aka, committers);
        Newcomer newcomerAlgorithm = new Newcomer(process.getGitRepository());
        Experience developerExperience = new Experience(process.getGitRepository());
        
        // Calculate developer trees
        try {

            DeveloperTreeManager.calculateDeveloperTrees(process.getGitRepository()
                    .getCommits(), periodLength);

        } catch (Exception ex) {
            Logger.getLogger(CalculateScattering.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Calculate periods
        List<Commit> commits = process.getGitRepository().getCommits();
        PeriodManager.calculatePeriods(commits, periodLength);
        List<Period> periods = PeriodManager.getList();

        // Load files and folders
        ScatteringMetricsParser structuralMP = new ScatteringMetricsParser();
        ScatteringMetricsParser semanticalMP = new ScatteringMetricsParser();
        File structuralScatteringFile = new File(scatteringFolderPath + projectName + "/"
                + periodLength + "/structuralScattering.csv");
        File semanticScatteringFile = new File(scatteringFolderPath + projectName + "/"
                + periodLength + "/semanticScattering.csv");

        // Load developer scattering metrics
        try {
            structuralMP.parseFile(structuralScatteringFile);
            semanticalMP.parseFile(semanticScatteringFile);

            // Calculating file complexity
            for (Period p : periods) {
                String periodsFolderPath = scatteringFolderPath + projectName + "/" + periodLength
                        + "/periodsData_1/";

                File periodsFolder = new File(periodsFolderPath);
                periodsFolder.mkdirs();
                String periodString = Integer.toString(p.getId());
                if (p.getId() < 10) {
                    periodString = "0" + p.getId();
                }
                File periodData = new File(periodsFolderPath + periodString + ".csv");
                PrintWriter pw1 = new PrintWriter(periodData);

                pw1.write("developer,"
                        + "structuralScattering,semanticScattering,"
                        + "isNewcomer," + "experience\n");

                List<Developer> developers = p.getDevelopers();

                for (Developer developer : developers) {
                    
                    Author author = new Author();
                    author.setEmail(developer.getEmail());
                    author.setName(developer.getName());
                    
                    double structuralScattering = structuralMP.getMetrics(developer.getEmail(), p.getId(),
                            ScatteringType.AVERAGE).getValue();
                    double semanticScattering = semanticalMP.getMetrics(developer.getEmail(), p.getId(),
                            ScatteringType.AVERAGE).getValue();

                    // find last commit of developer
                    int lastCommitIndexForAuthor = p.getCommits(developer).size()-1;
                    Commit lastCommitInPeriod = p.getCommits().get(lastCommitIndexForAuthor);
                    
                    boolean isNewcomer = newcomerAlgorithm.isNewComer(author, lastCommitIndexForAuthor);
                    int experienceValue = developerExperience.calculateExperienceBasedOnNumberOfCommit(author, lastCommitInPeriod);
                    
                    String message1 = developer.getEmail() + ","
                            + structuralScattering + "," + semanticScattering + ","
                            + isNewcomer + "," + developerExperience.getExperience(experienceValue) + "\n";

                    pw1.write(message1);
                    pw1.flush();

                }
            }
        } catch (NumberFormatException | IOException ex) {
            Logger.getLogger(CalculateScattering.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
