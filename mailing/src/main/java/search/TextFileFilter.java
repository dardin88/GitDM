package search;

import Exceptions.FileNullException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

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
       if(pathname==null)throw new FileNullException("Error: pathname nullo!");
       return pathname.getName().toLowerCase().endsWith(".txt");
    }
}