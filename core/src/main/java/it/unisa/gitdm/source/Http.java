/**
 *
 */
package it.unisa.gitdm.source;

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
class Http {

    public static String readFromURL(URL url) {
        String content = "";
        try {
            // Create the URL obect that points
            // at the default file index.html
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(
                    urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);

            // Read and print the lines from index.html
            while (true) {
                String nextLine = buff.readLine();
                if (nextLine != null) {
                    content += nextLine + "\n";
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
        return content;
    }
}
