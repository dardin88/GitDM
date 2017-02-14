package search;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Exceptions.FileNullException;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author depiano.it
 */
public class TextFileFilterTest {
    
    private TextFileFilter filefilter;
    public TextFileFilterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        this.filefilter=new TextFileFilter();
    }
    
    @After
    public void tearDown()
    {
        this.filefilter=null;
    }

    @Test
    public void TestAcceptNull()
    {
        try
        {
            this.filefilter.accept(null);
            fail("Error: il filtro accetta file nulli!");
        }
        catch(FileNullException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestInvalidPathAccept()
    {
        Assert.assertTrue(this.filefilter.accept(new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\msg0.txt")));
        Assert.assertFalse(this.filefilter.accept(new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\msg0.dat")));
        Assert.assertFalse(this.filefilter.accept(new File("jhsagbdjahgdbagdajda")));
        Assert.assertFalse(this.filefilter.accept(new File("")));
    }
}
