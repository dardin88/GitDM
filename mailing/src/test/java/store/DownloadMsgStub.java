/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author depiano.it
 */
public class DownloadMsgStub extends Store{
    
    public DownloadMsgStub(String name_project, int from_year, int from_month, int at_year, int at_month) {
        super(name_project, from_year, from_month, at_year, at_month);
    }
    
    @Override
    public Boolean download()
    {
        try
        {
            URL url = new URL("http://mail-archives.apache.org/mod_mbox/lucene-general/201410.mbox");

            File destination = new File(this.getDirStore().toString()+"\\201410.txt");

            //Copy bytes from the URL to the destination file.
            FileUtils.copyURLToFile(url, destination);
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }
}
