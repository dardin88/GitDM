package newGitTest;

import it.unisa.gitdm.bean.FileBean;
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
public class CleanTeaster {

    public CleanTeaster() {
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
            FileBean fb = new FileBean();
            fb.setPath("src/main/org/apache/tools/ant/types/Path.java");

            NewGit.clean(null);
            fail("il sistema accetta un file che non esiste");
        } catch (FileNotFoundException fi) {

        }
    }
}
