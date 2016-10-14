/**
 *
 */
package it.unisa.gitdm.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class IdentifyBugFixIsolationTest {

    public static void main(String[] args) {
        //String comment = "fixed bug # 555";
        //String comment = "fix bug # 555";
        //String comment = "fix bugs # 555";
        //String comment = "patched bug # 555";
        //String comment = "bug # 555";
        //String comment = "fixed # 555";
        //String comment = "patched # 555";
        String comment = "fixed bug #123 su una linea";

        String BugID = "555";
        String EXPR1 = "fix(e[ds])?|patch(ed)?[ \t]*(bugs?)?(defects?)?[# \t]*" + BugID;
        String EXPR2 = "(bugs?|pr|show_bug\\.cgi\\?id=)[# \t]*" + BugID;
        String EXPR3 = "[# \t]*" + BugID;

        Pattern pattern = Pattern.compile(EXPR1);
        Matcher matcher = pattern.matcher(comment);
        while (matcher.find()) {
            System.out.println("found");
        }

        Pattern pattern2 = Pattern.compile(EXPR2);
        Matcher matcher2 = pattern2.matcher(comment);
        while (matcher2.find()) {
            System.out.println("found_2");
        }

        Pattern pattern3 = Pattern.compile(EXPR3);
        Matcher matcher3 = pattern3.matcher(comment);
        while (matcher3.find()) {
            System.out.println("found_3");
        }

    }

}
