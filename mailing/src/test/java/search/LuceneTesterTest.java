/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import Exceptions.QueryEmptyException;
import Exceptions.QueryNullException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import store.DownloadMsgStub;

/**
 *
 * @author depiano.it
 */
public class LuceneTesterTest {
    
    private DownloadMsgStub driver;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        this.driver=new DownloadMsgStub("lucene-general",2010,4,2010,10); 
        if(!this.driver.download())
            fail("Inizializzaione Fallita!");
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void TestLuceneEmptySearch()
    {
        try
        {
            LuceneTester.test("");
            fail("Error: Permette la ricerca di una query vuota!");
        }
        catch(QueryEmptyException e)
        {
            //OK
        }
        catch(IOException e)
        {
            fail("Error: IOException in LuceneTester when query is empty!");
        }
        catch(ParseException e)
        {
            fail("Error: ParseException in LuceneTester when query is empty!");
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void TestLuceneNullSearch()
    {
        try
        {
            LuceneTester.test(null);
            fail("Error: Permette query null!");
        }
        catch(QueryNullException e)
        {
            //OK
        }
        catch (IOException ex)
        {
            fail("Error: IOException in LuceneTester when query is null!");
        }
        catch (ParseException ex)
        {
           fail("Error: ParseException in LuceneTester when query is null!");
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void TestLuceneValidSearch()
    {
        try
        {
            LuceneTester.test("lucene-general");
        }
        catch(QueryEmptyException e)
        {
            fail("Error: Query empty!");
        }
        catch(QueryNullException e)
        {
            fail("Error: Query Null!");
        }
        catch (IOException ex)
        {
            fail("Error: IOException in LuceneTester when query is:'lucene-general'");
        }
        catch (ParseException ex)
        {
            fail("Error: ParseException in LuceneTester when query is:'lucene-general'");
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void TestInvalidInputSearch()
    {
        try
        {
            LuceneTester.test("\\");
        }
        catch(QueryEmptyException e)
        {
            fail("Error: Query empty!");
        }
        catch(QueryNullException e)
        {
            fail("Error: Query Null!");
        }
        catch (IOException ex)
        {
            fail("Error: IOException in LuceneTester when query is:'\\'");
        }
        catch (ParseException ex)
        {
            //OK
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }
}
