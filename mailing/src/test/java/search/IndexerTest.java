/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author depiano.it
 */
public class IndexerTest {
    
    private Indexer indx;
    public IndexerTest() {
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
        try
        {
            this.indx=new Indexer("C:\\Users\\depiano.it\\Desktop\\index");
        }
        catch(IOException e)
        {
            fail("Error: Impossibile instanziare Indexer!");
        }
    }
    
    @After
    public void tearDown()
    {
        this.indx=null;
    }

    
}
