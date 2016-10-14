/**
 *
 */
package it.unisa.gitdm.source;

import it.unisa.gitdm.bean.Bug;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class BugZilla {

    public static List<Bug> extractBug(String path, String product) {
        String log = generateLogFile(path, product);
        // System.out.println("LOG: "+log);

        return generateBugs(log);
    }

    private static String generateLogFile(String address, String product) {
        String option = "buglist.cgi?bug_status=RESOLVED&resolution=FIXED&no_redirect=1&product="
                + product
                + "&query_format=specific&ctype=csv&human=1&columnlist=product%2Ccomponent%2Cassigned_to%2Cbug_status%2Cresolution%2Cshort_desc%2Cchangeddate%2Copendate";
        URL url = null;

        if (address.charAt(address.length() - 1) != '/') {
            address += '/';
        }

        try {
            url = new URL(address + option);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BugZilla.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Http.readFromURL(url);
    }

    private static List<Bug> generateBugs(String log) {
        List<Bug> bugs = new ArrayList<>();

        // Skip first line
        try (Scanner scanner = new Scanner(log)) {
            // Skip first line
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bugInfo = line.split(",");
                if (bugInfo.length == 9) {
                    Bug bug = new Bug();
                    bug.setID(bugInfo[0]);
                    bug.setProduct(bugInfo[1].replace("\"", ""));
                    bug.setComponent(bugInfo[2].replace("\"", ""));
                    bug.setAssignedTo(bugInfo[3].replace("\"", ""));
                    bug.setStatus(bugInfo[4].replace("\"", ""));
                    bug.setResolution(bugInfo[5].replace("\"", ""));
                    bug.setSubject(bugInfo[6].replace("\"", ""));
                    SimpleDateFormat formatter = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    java.util.Date badDate;
                    badDate = formatter.parse(bugInfo[7].replace("\"", ""));
                    Timestamp date = new Timestamp(badDate.getTime());
                    bug.setLastChangedTime(date.getTime());

                    java.util.Date badDate2;
                    badDate2 = formatter.parse(bugInfo[8].replace("\"", ""));

                    Timestamp date2 = new Timestamp(badDate2.getTime());
                    bug.setReportedTime(date2.getTime());

                    bugs.add(bug);
                }

            }
        } catch (ParseException ex) {
            Logger.getLogger(BugZilla.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bugs;

    }

}
