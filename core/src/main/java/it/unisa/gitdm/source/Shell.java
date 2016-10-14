/**
 *
 */
package it.unisa.gitdm.source;

import it.unisa.gitdm.bean.FileBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class Shell {

    //TODO Windows!
    public static int getNumberOfLine(File directory, FileBean file) throws IOException {
        String cmd = "wc -l " + directory.getAbsolutePath() + "/" + file.getPath();
        System.out.println("cmd: " + cmd);
        String line;
        String content = "";

        Process p = Runtime.getRuntime().exec(cmd);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                content = line.split(" ")[0];

            }
        }

        return Integer.parseInt(content);
    }

}
