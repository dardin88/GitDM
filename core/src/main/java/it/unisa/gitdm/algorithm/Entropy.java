/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.metrics.semanticMetric.CosineSimilarity;
import it.unisa.gitdm.source.Git;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Entropy {

    private final GitRepository gitRepository;
    private final CosineSimilarity cosineSimilarity = new CosineSimilarity();
    private String baseFolderPath;
    private String projectName;
    private int numOpenFile = 0;

    Entropy(GitRepository repository) {
        this.gitRepository = repository;
    }

    public static void calculateAverageGlobalEntropy(String pathOutputFile, String pathInputFile) {
        try {
            try (FileWriter fw = new FileWriter(pathOutputFile); PrintWriter pw = new PrintWriter(fw)) {
                // Template input file
                // ("Commit, Author, isBug, Entropy, Num of File Touched")
                int numBugCommit = 0;
                int numNonBugCommit = 0;
                int totalNumTouchedFileByBugCommit = 0;
                int totalNumTouchedFileByNonBugCommit = 0;
                double totalBugCommitEntropy = 0;
                double totalNonBugCommitEntropy = 0;
                String line;
                BufferedReader br;
                br = new BufferedReader(new FileReader(pathInputFile));
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] commitInfo = line.split(",");
                    if (commitInfo.length < 2 || line.contains("NaN")) {
                        continue;
                    }
                    if (commitInfo[2].equalsIgnoreCase("true")) {
                        numBugCommit++;
                        totalNumTouchedFileByBugCommit += Integer
                                .parseInt(commitInfo[4].replace(" ", ""));
                        totalBugCommitEntropy += Double.parseDouble(commitInfo[3]
                                .replace(" ", ""));
                    } else {
                        numNonBugCommit++;
                        totalNumTouchedFileByNonBugCommit += Integer
                                .parseInt(commitInfo[4].replace(" ", ""));
                        totalNonBugCommitEntropy += Double
                                .parseDouble(commitInfo[3].replace(" ", ""));
                    }
                }
                double averageBugCommitEntropy = totalBugCommitEntropy / numBugCommit;
                double averageNonBugCommitEntropy = totalNonBugCommitEntropy
                        / numNonBugCommit;
                double averageTouchedFileByBugCommit = (double) totalNumTouchedFileByBugCommit
                        / numBugCommit;
                double averageTouchedFileByNonBugCommit = (double) totalNumTouchedFileByNonBugCommit
                        / numNonBugCommit;
                pw.println("Num non-Bug Commit," + numNonBugCommit);
                pw.println("Num Bug Commit," + numBugCommit);
                pw.println();
                pw.println("Total non-Bug Entropy," + totalNonBugCommitEntropy
                        + ", Average," + averageNonBugCommitEntropy);
                pw.println("Total Bug Entropy," + totalBugCommitEntropy + ", Average,"
                        + averageBugCommitEntropy);
                pw.println("Total Touched File By Bug Commit,"
                        + totalNumTouchedFileByBugCommit + ", Average,"
                        + averageTouchedFileByBugCommit);
                pw.println("Total Touched File By Non-Bug Commit,"
                        + totalNumTouchedFileByNonBugCommit + ", Average,"
                        + averageTouchedFileByNonBugCommit);
            }
        } catch (IOException ex) {
            Logger.getLogger(Entropy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static double mean(List<Double> m) {
        double sum = 0;
        for (Double d : m) {
            sum = sum + d;
        }
        return sum / m.size();
    }

    static double median(List<Double> m) {
        Collections.sort(m);

        if ((m.size() / 2) % 2 == 0) {
            return m.get(m.size() / 2);
        } else {
            return m.get(((m.size() / 2) + ((m.size() + 1) / 2)) / 2);
        }
    }

    static double stDev(List<Double> m) {
        Collections.sort(m);

        double mean = mean(m);

        double sd = 0;

        for (Double value : m) {
            sd = sd + Math.pow(value - mean, 2);
        }

        return Math.sqrt(sd / (m.size()));
    }

    static double min(List<Double> m) {
        Collections.sort(m);

        return m.get(0);
    }

    double[][] calculateDirectoryEntropy(List<FileBean> fileBeans,
                                         Commit commit) {
        this.numOpenFile = this.numOpenFile + fileBeans.size() * 2;

        double[][] conceptualCoupling = new double[fileBeans.size()][fileBeans
                .size()];

        for (int i = 0; i < fileBeans.size(); i++) {

            for (int j = 0; i < fileBeans.size(); i++) {

                if (fileBeans.get(i) != fileBeans.get(j)) {

                    if (i > j) {

                        try {
                            String[] documentOne = new String[2];
                            documentOne[0] = "docOne" + i;

                            String[] documentTwo = new String[2];
                            documentTwo[0] = "docTwo" + j;

                            documentOne[1] = Git.gitShow(
                                    this.gitRepository.getDirectory(),
                                    fileBeans.get(i), commit);
                            documentTwo[1] = Git.gitShow(
                                    this.gitRepository.getDirectory(),
                                    fileBeans.get(j), commit);

                            double similarity = this.cosineSimilarity
                                    .computeSimilarity(documentOne, documentTwo);

                            conceptualCoupling[i][j] = similarity;
                            conceptualCoupling[j][i] = similarity;
                        } catch (IOException ex) {
                            Logger.getLogger(Entropy.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }

        }

        return conceptualCoupling;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBaseFolder() {
        return this.baseFolderPath;
    }

    public void setBaseFolder(String baseFolderPath) {
        this.baseFolderPath = baseFolderPath;
    }
}
