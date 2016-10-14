/**
 *
 */
package it.unisa.gitdm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class BugZillaCsvTest {

    public static void main(String args[]) {

        String nextLine;
        try {
            // Create the URL obect that points
            // at the default file index.html
            URL url = new URL("https://issues.apache.org/bugzilla/buglist.cgi?bug_status=RESOLVED&resolution=FIXED&content=&no_redirect=1&product=&query_format=specific&ctype=csv&human=1&columnlist=product%2Ccomponent%2Cassigned_to%2Cbug_status%2Cresolution%2Cshort_desc%2Cchangeddate%2Copendate");
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(
                    urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);

            //System.out.println("Path: "+url.getPath());
            //System.out.println("Ref: "+url.getRef());
            // Read and print the lines from index.html
            while (true) {
                nextLine = buff.readLine();
                if (nextLine != null) {
                    System.out.println(nextLine);
                } else {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Please check the URL:"
                    + e.toString());
        } catch (IOException e1) {
            System.out.println("Can't read  from the Internet: "
                    + e1.toString());
        }
    }
}
