/**
 *
 */
package it.unisa.gitdm.algorithm;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class CSV {

    public static void connectCsv(String path, int numOfAuthors, String out) {
        String line;

        try (FileWriter fw = new FileWriter(out); PrintWriter pw = new PrintWriter(fw)) {
            boolean firstFirsLine = true;
            for (int i = 0; i < numOfAuthors; i++) {
                System.out.println(i);
                boolean firstLine = true;
                String csvFile = path + i + ".csv";
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        if (firstLine) {
                            if (firstFirsLine) {
                                pw.println(line);
                                firstFirsLine = false;
                            }
                            firstLine = false;
                            continue;
                        }
                        if (!line.contains("NaN")) {
                            pw.println(line);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Closing the statistic's file
            pw.flush();
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void connectCsvFiltered(String path, int numOfAuthors, String out) {
        String line;
        try (FileWriter fw = new FileWriter(out);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("id,bug,ccdb,ccdbm,ccdbmm,kdb,cct,cctm,cctmm,kt");
            for (int i = 0; i < numOfAuthors; i++) {
                System.out.println(i);
                boolean firstLine = true;
                String csvFile = path + i + ".csv";
                BufferedReader br;
                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    if (!line.contains("NaN")) {
                        String[] commitInfo = line.split(",");
                        if (commitInfo.length > 18) {
                            pw.println(commitInfo[3] + "," + commitInfo[7]
                                    + "," + commitInfo[12] + ","
                                    + commitInfo[13] + "," + commitInfo[14]
                                    + "," + commitInfo[15] + ","
                                    + commitInfo[16] + "," + commitInfo[17]
                                    + "," + commitInfo[18] + ","
                                    + commitInfo[19]);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
