package newGitTest;

import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.gitException.CommitHashShortException;
import it.unisa.gitdm.gitException.CommitNotFound;
import it.unisa.gitdm.gitException.CommithashInvalidFormatException;
import it.unisa.gitdm.gitException.CommithashLongException;
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
public class CheckoutTest {

    public CheckoutTest() {
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
        Commit co = new Commit();
        co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38");

        File f = new File("aaaa");
        try {
            NewGit.gitCheckout(f, co);
            fail("il sistema accetta un file che non esiste");
        } catch (FileNotFoundException fi) {

        } catch (CommitHashShortException c) {
            fail("CommitHashShortException in checkout");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in checkout");

        } catch (CommithashLongException c) {
            fail("CommithashLongException in checkout");
        } catch (CommitNotFound c) {
            fail("CommitFound in checkout");

        }
    }

    @Test
    public void commitHashShortTest() throws GitAPIException, IOException {

        try {
            Commit co = new Commit();
            co.setCommitHash("bce318");
            File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");
            NewGit.gitCheckout(f, co);
            fail("il sistema accetta un fcommitHash breve");
        } catch (CommitHashShortException c) {

        } catch (FileNotFoundException fi) {
            fail("CommitHashShortException in checkout");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in checkout");

        } catch (CommithashLongException c) {
            fail("CommithashLongException in checkout");
        } catch (CommitNotFound c) {
            fail("CommitFound in checkout");

        }
    }

    @Test
    public void commitHashLongTest() throws GitAPIException, IOException {
        Commit co = new Commit();
        co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e087e38aaaaaaaaaaaa");
        File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");

        try {
            NewGit.gitCheckout(f, co);
            fail("il sistema accetta un fcommitHash lungo");
        } catch (CommithashLongException c) {

        } catch (FileNotFoundException fi) {
            fail("CommitHashShortException in checkout");
        } catch (CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in checkout");

        } catch (CommitHashShortException c) {
            fail("CommithashLongException in checkout");
        } catch (CommitNotFound c) {
            fail("CommitFound in checkout");

        }
    }

    @Test
    public void commitFormatTest() throws GitAPIException, IOException {
        Commit co = new Commit();
        co.setCommitHash("@@2845215f55eb63f0ad911c1610eac4dfaf82b4");
        File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");

        try {
            NewGit.gitCheckout(f, co);
            fail("il sistema accetta un fcommitHash lungo");
        } catch (CommithashInvalidFormatException c) {

        } catch (FileNotFoundException fi) {
            fail("CommitHashShortException in checkout");
        } catch (CommithashLongException c) {
            fail("CommithashInvalidFormatException in checkout");

        } catch (CommitHashShortException c) {
            fail("CommithashLongException in checkout");
        } catch (CommitNotFound c) {
            fail("CommitFound in checkout");

        }
    }

    @Test
    public void commitNotFound() throws GitAPIException, IOException {
        Commit co = new Commit();
        co.setCommitHash("bce31805e9b4b1360d50be8e001886d58e08@@");
        File f = new File("C:\\Users\\Vincenzo\\Desktop\\provaoutput\\ant");

        try {
            NewGit.gitCheckout(f, null);
            fail("il sistema accetta un commit che non esiste");
        } catch (CommitNotFound c) {

        } catch (FileNotFoundException fi) {
            fail("CommitHashShortException in checkout");
        } catch (CommithashLongException | CommithashInvalidFormatException c) {
            fail("CommithashInvalidFormatException in checkout");

        } catch (CommitHashShortException c) {
            fail("CommithashLongException in checkout");
        }
    }

}
