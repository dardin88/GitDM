package newGitTest;

import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.gitException.CommitHashShortException;
import it.unisa.gitdm.gitException.CommitNotFound;
import it.unisa.gitdm.gitException.CommithashInvalidFormatException;
import it.unisa.gitdm.gitException.CommithashLongException;
import it.unisa.gitdm.gitException.FileNameInvalidFormatException;
import it.unisa.gitdm.gitException.FileNameShortException;
import it.unisa.gitdm.source.NewGit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Vincenzo
 */
public class ShowTest {

    public ShowTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void InvalidDirectoryTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            FileBean fb = new FileBean();
            File f = new File("aaa");
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un file che non esiste");
        } catch (FileNotFoundException fi) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in show");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in show");

        }

    }

    @Test
    public void fileShortTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("s");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un file con una lunghezza minima");
        } catch (FileNameShortException fi) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in show");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        }

    }

    @Test
    public void fileformattTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("@@src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un file con fprmato non valido");
        } catch (FileNameInvalidFormatException fi) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in show");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        } catch (FileNameShortException fi) {

            fail("FileNameShortException in show");
        }

    }

    @Test
    public void commitShortTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989a");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un commithash breve");
        } catch (CommitHashShortException fi) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        } catch (FileNameShortException fi) {

            fail("FileNameShortException in show");

        } catch (FileNameInvalidFormatException fi) {

            fail("FileNameInvalidFormatException in show");
        }

    }

    @Test
    public void commitLongTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989a5vfg55s5s5sd5s5d55sd5sd5sd5s5ds5d5d5sdsdfegdgfdgfs");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un commithash lungo");
        } catch (CommithashLongException c) {

        } catch (CommitHashShortException fi) {
            fail("CommitHashShortException in show");

        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        } catch (FileNameShortException fi) {

            fail("FileNameShortException in show");

        } catch (FileNameInvalidFormatException fi) {

            fail("FileNameInvalidFormatException in show");
        }

    }

    @Test
    public void commitFormatTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e966408//");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, co);
            fail("il sistema accetta un commithash che non rispetta il formato");
        } catch (CommithashInvalidFormatException c) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommitHashShortException fi) {
            fail("CommitHashShortException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        } catch (FileNameShortException fi) {

            fail("FileNameShortException in show");

        } catch (FileNameInvalidFormatException fi) {

            fail("FileNameInvalidFormatException in show");
        }

    }

    @Test
    public void invalidcommitTest() throws GitAPIException, IOException {

        try {

            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e966408//");
            FileBean fb = new FileBean();
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.gitShow(f, fb, null);
            fail("il sistema accetta un commithash che non esiste");
        } catch (CommitNotFound c) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in show");

        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatExceptionin show");

        } catch (CommitHashShortException fi) {
            fail("CommitHashShortException in show");

        } catch (FileNotFoundException fi) {

            fail("FileNotFoundException in show");
        } catch (FileNameShortException fi) {

            fail("FileNameShortException in show");

        } catch (FileNameInvalidFormatException fi) {

            fail("FileNameInvalidFormatException in show");
        }

    }

}
