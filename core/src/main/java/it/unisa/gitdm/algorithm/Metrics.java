/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.metrics.CKMetrics;
import it.unisa.gitdm.metrics.ReadSourceCode;
import it.unisa.gitdm.metrics.parser.bean.ClassBean;
import it.unisa.gitdm.source.Git;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Metrics {

    private final GitRepository gitRepository;

    public Metrics(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public static int numOfOccurrence(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = str.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    public void calculateMetrics(String pathOutputFile) {
        try (FileWriter fw = new FileWriter(pathOutputFile);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("Commit, isBug, LOC, CBO, LCOM, NOM, RFC, WMC");

            int numOfBugCommit = 0;
            double bugLOC = 0;
            double bugCBO = 0;
            double bugLCOM = 0;
            double bugNOM = 0;
            double bugRFC = 0;
            double bugWMC = 0;

            int numOfNonBugCommit = 0;
            double nonbugLOC = 0;
            double nonbugCBO = 0;
            double nonbugLCOM = 0;
            double nonbugNOM = 0;
            double nonbugRFC = 0;
            double nonbugWMC = 0;

            int index = 0;
            for (Commit commit : gitRepository.getCommits()) {
                index++;
                System.out.println("Commit: " + index + " / "
                        + gitRepository.getCommits().size());

                int numOfJavaFiles = 0;

                double LOC = 0;
                double CBO = 0;
                double LCOM = 0;
                double NOM = 0;
                double RFC = 0;
                double WMC = 0;

                for (Change change : commit.getChanges()) {
                    FileBean file = change.getFile();
                    if (file.getPath().contains(".java")) {

                        // Contenuto del file
                        String source = Git.gitShow(gitRepository.getDirectory(),
                                file, commit);

                        ArrayList<ClassBean> code = new ArrayList<>();

                        ReadSourceCode.readSourceCodeFromString(source, code);

                        // aggiorno il totale delle metriche
                        for (ClassBean c : code) { // dovrebbe esserci una sola
                            // ClassBean
                            numOfJavaFiles++;
                            LOC = LOC + CKMetrics.getLOC(c);
                            CBO = CBO + CKMetrics.getCBO(c);
                            LCOM = LCOM + CKMetrics.getLCOM(c);
                            NOM = NOM + CKMetrics.getNOM(c);
                            RFC = RFC + CKMetrics.getRFC(c);
                            WMC = WMC + CKMetrics.getWMC(c);

                            if (commit.isBug()) {
                                numOfBugCommit++;
                                bugLOC = LOC + bugLOC;
                                bugCBO = CBO + bugCBO;
                                bugLCOM = LCOM + bugLCOM;
                                bugNOM = NOM + bugNOM;
                                bugRFC = RFC + bugRFC;
                                bugWMC = WMC + bugWMC;
                            } else {
                                numOfNonBugCommit++;
                                nonbugLOC = LOC + nonbugLOC;
                                nonbugCBO = CBO + nonbugCBO;
                                nonbugLCOM = LCOM + nonbugLCOM;
                                nonbugNOM = NOM + nonbugNOM;
                                nonbugRFC = RFC + nonbugRFC;
                                nonbugWMC = WMC + nonbugWMC;
                            }
                        }
                    }
                }

                // Calcolo le medie del commit
                double averageLOC = LOC / numOfJavaFiles;
                double averageCBO = CBO / numOfJavaFiles;
                double averageLCOM = LCOM / numOfJavaFiles;
                double averageNOM = NOM / numOfJavaFiles;
                double averageRFC = RFC / numOfJavaFiles;
                double averageWMC = WMC / numOfJavaFiles;

                pw.println(commit.getCommitHash() + "," + commit.isBug() + ","
                        + averageLOC + "," + averageCBO + "," + averageLCOM + ","
                        + averageNOM + "," + averageRFC + "," + averageWMC);
            }

            pw.println();
            pw.println("Average");

            pw.println("Commit Type, Number of Commits, LOC, CBO, LCOM, NOM, RFC, WMC");
            double averageLOCbug = bugLOC / numOfBugCommit;
            double averageCBObug = bugCBO / numOfBugCommit;
            double averageLCOMbug = bugLCOM / numOfBugCommit;
            double averageNOMbug = bugNOM / numOfBugCommit;
            double averageRFCbug = bugRFC / numOfBugCommit;
            double averageWMCbug = bugWMC / numOfBugCommit;

            pw.println("Bugs," + numOfBugCommit + "," + averageLOCbug + ","
                    + averageCBObug + "," + averageLCOMbug + "," + averageNOMbug
                    + "," + averageRFCbug + "," + averageWMCbug);

            double averageLOCnonbug = nonbugLOC / numOfNonBugCommit;
            double averageCBOnonbug = nonbugCBO / numOfNonBugCommit;
            double averageLCOMnonbug = nonbugLCOM / numOfNonBugCommit;
            double averageNOMnonbug = nonbugNOM / numOfNonBugCommit;
            double averageRFCnonbug = nonbugRFC / numOfNonBugCommit;
            double averageWMCnonbug = nonbugWMC / numOfNonBugCommit;

            pw.println("Non-Bugs," + numOfNonBugCommit + "," + averageLOCnonbug
                    + "," + averageCBOnonbug + "," + averageLCOMnonbug + ","
                    + averageNOMnonbug + "," + averageRFCnonbug + ","
                    + averageWMCnonbug);
        } catch (IOException ex) {
            Logger.getLogger(Metrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateMetricsLog(String pathOutputFile) {
        try (FileWriter fw = new FileWriter(pathOutputFile);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("Commit, isBug, LOC, CBO, LCOM, NOM, RFC, WMC, MPC");

            int index = 0;
            for (Commit commit : gitRepository.getCommits()) {
                List<ClassBean> classesOfCommit = new ArrayList<>();
                index++;
                System.out.println("Commit: " + index + " / "
                        + gitRepository.getCommits().size());

                int numOfJavaFiles = 0;

                double LOC = 0;
                double CBO = 0;
                double LCOM = 0;
                double NOM = 0;
                double RFC = 0;
                double WMC = 0;

                for (Change change : commit.getChanges()) {
                    FileBean file = change.getFile();
                    if (file.getPath().contains(".java")) {

                        // Contenuto del file
                        String source = Git.gitShow(gitRepository.getDirectory(),
                                file, commit);

                        ArrayList<ClassBean> code = new ArrayList<>();
                        ReadSourceCode.readSourceCodeFromString(source, code);

                        // aggiorno il totale delle metriche
                        for (ClassBean c : code) { // dovrebbe esserci una sola
                            // ClassBean
                            numOfJavaFiles++;
                            classesOfCommit.add(c);
                            LOC = LOC + CKMetrics.getLOC(c);
                            // CBO = (double) CBO + CKMetrics.getCBO(c);
                            CBO = CBO + numOfOccurrence(source, "import");
                            LCOM = LCOM + CKMetrics.getLCOM(c);
                            NOM = NOM + CKMetrics.getNOM(c);
                            RFC = RFC + CKMetrics.getRFC(c);
                            WMC = WMC + CKMetrics.getWMC(c);
                        }
                    }
                }

                int numOfCalculations = 0;
                double MPC = 0;
                for (ClassBean c : classesOfCommit) {
                    for (ClassBean cc : classesOfCommit) {
                        numOfCalculations++;
                        MPC = MPC + CKMetrics.getMPC(c, cc);
                    }
                }

                // Calcolo le medie del commit
                double averageLOC = LOC / numOfJavaFiles;
                double averageCBO = CBO / numOfJavaFiles;
                double averageLCOM = LCOM / numOfJavaFiles;
                double averageNOM = NOM / numOfJavaFiles;
                double averageRFC = RFC / numOfJavaFiles;
                double averageWMC = WMC / numOfJavaFiles;
                double averageMPC = MPC / numOfCalculations;

                pw.println(commit.getCommitHash() + "," + commit.isBug() + ","
                        + averageLOC + "," + averageCBO + "," + averageLCOM + ","
                        + averageNOM + "," + averageRFC + "," + averageWMC + ","
                        + averageMPC);
            }
        } catch (IOException ex) {
            Logger.getLogger(Metrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
