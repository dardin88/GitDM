package newGitTest;

import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.gitException.CommitHashShortException;
import it.unisa.gitdm.gitException.CommitNotFound;
import it.unisa.gitdm.gitException.CommithashInvalidFormatException;
import it.unisa.gitdm.gitException.CommithashLongException;
import it.unisa.gitdm.gitException.FileNameInvalidFormatException;
import it.unisa.gitdm.gitException.FileNameShortException;
import it.unisa.gitdm.gitException.InvalidNumberLineException;
import it.unisa.gitdm.gitException.ValueNumberLineOutOfRangeException;
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
public class GitBlameTest {

    public GitBlameTest() {
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
            File f = new File("aaaa");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 1, fg);

            fail("il sistema accetta un file che non esiste");
        } catch (FileNotFoundException fi) {

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");
        } catch (CommitNotFound c) {
            fail("CommitFound in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException in blame");
        }
    }

    @Test
    public void commitHashShortTest() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 1, fg);
            fail("il sistema accetta un commitHash breve");
        } catch (CommitHashShortException c) {

        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");
        } catch (CommitNotFound c) {
            fail("CommitFound in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException in blame");
        }
    }

    @Test
    public void commitHashLongTest() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38aaaaaaaaaaaaaaaaaaaaaaaa");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 1, fg);
            fail("il sistema accetta un fcommitHash lungo");
        } catch (CommithashLongException c) {

        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommitNotFound c) {
            fail("CommitFound in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException in blame");
        }
    }

    @Test
    public void commitFormatTest() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e@@");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 1, fg);
            fail("il sistema accetta un formato commitHash non valido");
        } catch (CommithashInvalidFormatException c) {

        } catch (CommithashLongException c) {
            fail("CommithashLongException in checkout");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortExceptionin blame");
        } catch (CommitNotFound c) {
            fail("CommitFound in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException in blame");
        }
    }

    @Test
    public void commitNotFound() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, null, 1, fg);
            fail("il sistema accetta un commit che non esiste");
        } catch (CommitNotFound c) {

        } catch (FileNotFoundException fi) {
            fail("CommitHashShortException in blame");
        } catch (CommithashLongException | CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (CommitHashShortException c) {
            fail("CommithashLongException in blame");
        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException in blame");
        }
    }

    @Test
    public void valueNumberLine() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 0, fg);
            fail("il sistema accetta il numero di linea 0");
        } catch (InvalidNumberLineException i) {

        } catch (CommitNotFound c) {
            fail("CommitNotFound in blame");

        } catch (FileNotFoundException fi) {
            fail("FileNotFoundException in blame");
        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        }

    }

    @Test
    public void valueLongNumberLineTest() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            String fg = "src/main/org/apache/tools/ant/types/Path.java";
            NewGit.gitBlame(f, co, 8000, fg);
            fail("il sistema accetta il numero di linea maggiore delle righe del file");
        } catch (ValueNumberLineOutOfRangeException i) {

        } catch (CommitNotFound c) {
            fail("CommitNotFound in blame");

        } catch (FileNotFoundException fi) {
            fail("FileNotFoundException in blame");
        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (InvalidNumberLineException i) {
            fail("il sistema accetta il numero di linea 0");

        }

    }

    @Test
    public void fileNameShort() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            String fg = "a";
            NewGit.gitBlame(f, co, 1, fg);
            fail("il sistema accetta un nome file breve");
        } catch (FileNameShortException i) {

        } catch (CommitNotFound c) {
            fail("CommitNotFound in blame");

        } catch (FileNotFoundException fi) {
            fail("FileNotFoundException in blame");
        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException");

        } catch (FileNameInvalidFormatException i) {
            fail("FileNameInvalidFormatException");

        }

    }

    @Test
    public void fileNameFormatTest() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            String fg = "src/main/org/apache/tools/ant/types/@Path.java";
            NewGit.gitBlame(f, co, 1, fg);
            fail("il sistema accetta un nome file con un formato non valido");
        } catch (FileNameInvalidFormatException i) {

        } catch (FileNameShortException i) {

            fail("FileNameShortException");
        } catch (CommitNotFound c) {
            fail("CommitNotFound in blame");

        } catch (FileNotFoundException fi) {
            fail("FileNotFoundException in blame");
        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException");

        }

    }

    @Test
    public void fileNameNull() throws GitAPIException, IOException {

        try {
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            Commit co = new Commit();
            co.setCommitHash("1ccf1989ac4c780845737fcb6b5e9b7e9664084b");
            String fg = "src/main/org/apache/tools/ant/types/@Path.java";
            NewGit.gitBlame(f, co, 1, null);
            fail("il sistema accetta un nome file che non esiste");
        } catch (FileNotFoundException fil) {

        } catch (FileNameShortException i) {

            fail("FileNameShortException");
        } catch (CommitNotFound c) {
            fail("CommitNotFound in blame");

        } catch (CommithashLongException c) {
            fail("CommithashLongException in blame");

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in blame");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in blame");

        } catch (InvalidNumberLineException i) {
            fail("InvalidNumberLineException");

        } catch (FileNameInvalidFormatException i) {
            fail("FileNameInvalidFormatException");

        }

    }

}
