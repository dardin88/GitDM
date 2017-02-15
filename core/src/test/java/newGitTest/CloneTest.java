package newGitTest;

import it.unisa.gitdm.gitException.DirectoryInvalidFormatException;
import it.unisa.gitdm.gitException.DirectoryshortException;
import it.unisa.gitdm.gitException.ProjectNameInvalidFormatExceptions;
import it.unisa.gitdm.gitException.ProjectNameShortException;
import it.unisa.gitdm.gitException.ProjectNullException;
import it.unisa.gitdm.gitException.UrlInvalidFormatExceptions;
import it.unisa.gitdm.gitException.UrlNullException;
import it.unisa.gitdm.gitException.UrlshortException;
import it.unisa.gitdm.source.NewGit;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Vincenzo
 */
public class CloneTest {

    @Test
    public void TestUrlNull() throws IOException, InterruptedException {
        String dir = "C:\\Users\\Vincenzo\\Desktop\\provaoutput\\";
        String url = null;

        try {

            NewGit.clone(url, true, " ", dir);
            fail("prende in input un project name nullo");
        } catch (UrlNullException u) {

        } catch (UrlshortException u) {
            fail("UrlshortException in clone");
        } catch (UrlInvalidFormatExceptions ui) {
            fail("UrlInvalidFormatExceptions in clone ");
        }

    }

    @Test
    public void TestUrlshort() throws IOException, InterruptedException {
        String dir = "C:\\Users\\Vincenzo\\Desktop\\provaoutput\\";
        String url = "htts";

        try {

            NewGit.clone(url, true, " ", dir);
            fail("prende in input un url breve");
        } catch (UrlNullException u) {
            fail("UrlNullException in clone");
        } catch (UrlshortException u) {

        } catch (UrlInvalidFormatExceptions ui) {
            fail("UrlInvalidFormatExceptions in clone ");
        }

    }

    @Test
    public void TestUrlformat() throws IOException, InterruptedException {
        String dir = "C:\\Users\\Vincenzo\\Desktop\\provaoutput\\";
        String url = "httshhkahkakbalajgbalbalalbablaba";

        try {

            NewGit.clone(url, true, " ", dir);
            fail("prende in input un url con un formato non valido");
        } catch (UrlNullException u) {
            fail("UrlNullException in clone");
        } catch (UrlshortException u) {
            fail("UrlshortException in clone ");
        } catch (UrlInvalidFormatExceptions ui) {

        }

    }

    @Test
    public void TestProjectnameshort() throws IOException, InterruptedException {
        String dir = "C:\\Users\\Vincenzo\\Desktop\\provaoutput\\";
        String url = "https://github.com/apache/ant.git";

        try {

            NewGit.clone(url, true, "a", dir);
            fail("prende in input un project name breve");
        } catch (ProjectNullException p) {
            fail("ProjectNullExceptionin clone");
        } catch (ProjectNameShortException p) {

        } catch (ProjectNameInvalidFormatExceptions p) {
            fail("ProjectNameInvalidFormatExceptions in clone ");
        }

    }

    @Test
    public void TestProjectnameformat() throws IOException, InterruptedException {
        String dir = "C:\\Users\\Vincenzo\\Desktop\\provaoutput\\";
        String url = "https://github.com/apache/ant.git";

        try {

            NewGit.clone(url, true, "a@!!!<>", dir);
            fail("prende in input un project name con un formato non valido");
        } catch (ProjectNullException p) {
            fail("ProjectNullExceptionin clone");
        } catch (ProjectNameShortException p) {
            fail("ProjectNameShortException in clone ");
        } catch (ProjectNameInvalidFormatExceptions p) {

        }

    }

    @Test
    public void TestDirectoryShort() throws IOException, InterruptedException {
        String dir = "a ";
        String url = "https://github.com/apache/ant.git";

        try {
            NewGit.clone(url, true, "ant", dir);
            fail("prende in input una directory breve");
        } catch (FileNotFoundException p) {
            fail("FileNotFoundException clone");
        } catch (DirectoryshortException p) {

        } catch (DirectoryInvalidFormatException p) {
            fail("directoryInvalidFormatExceptions in clone ");
        }

    }

    @Test
    public void TestDirectoryformat() throws IOException, InterruptedException {
        String dir = "a<>! ";
        String url = "https://github.com/apache/ant.git";

        try {
            NewGit.clone(url, true, "ant", dir);
            fail("prende in input una directory con un formato non valido");
        } catch (FileNotFoundException p) {
            fail("FileNotFoundException clone");
        } catch (DirectoryshortException p) {
            fail("directoryshortException in clone ");
        } catch (DirectoryInvalidFormatException p) {

        }

    }

}
