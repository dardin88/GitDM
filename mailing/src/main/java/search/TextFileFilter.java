package search;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class TextFileFilter implements FileFilter
{
    public boolean accept(File pathname)
    {
       return pathname.getName().toLowerCase().endsWith(".txt");
    }
}