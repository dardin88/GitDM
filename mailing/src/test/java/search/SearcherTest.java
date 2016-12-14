 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import Exceptions.QueryEmptyException;
import Exceptions.QueryNullException;
import Exceptions.ScoreDocNullException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;
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
public class SearcherTest {
    
    private LuceneTesterStub driver;
    private Searcher srh;
    
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
            this.driver=new LuceneTesterStub();
            this.driver.createIndex();
            this.srh=new Searcher("C:\\Users\\depiano.it\\Desktop\\index");
        }
        catch (IOException ex)
        {
            fail("Error: IOException nel costruttore Searcher!");
        }
    }
    
    @After
    public void tearDown()
    {
        this.driver=null;
        this.srh=null;
    }
    
    @Test
    public void TestGetDocumentNull()
    {
        try
        {
            Document d;
            d = this.srh.getDocument(null);
            fail("Error: Permette un scoreDoc nullo!");
        }
        catch(CorruptIndexException e)
        {
            fail("Error: CorruptIndexException in GetDocument()");
        }
        catch(IOException e)
        {
            fail("Error: IOException in GetDocument()");
        }
        catch(ScoreDocNullException e)
        {
            //OK
        }
    }

    @Test
    public void TestEmptySearch()
    {
        try
        {
            TopDocs tp=this.srh.search("");
            fail("Error: Permette una query vuota!");
        }
        catch(QueryEmptyException e)
        {
            //ok
        }
        catch(QueryNullException e)
        {
            fail("Error: viene lanciata l'eccezione QueryNullException invece che QueryEmptyException");
        }
        catch(IOException e)
        {
            fail("Error: viene lanciata l'eccezione IOException invece che QueryEmptyException");
        }
        catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            fail("Error: viene lanciata l'eccezione ParseException invece che QueryEmptyException");
        }
    }
    
    @Test
    public void TestNullSearch()
    {
        try
        {
            TopDocs tp=this.srh.search(null);
            fail("Error: Permette una query NULL!");
        }
        catch(QueryEmptyException e)
        {
            fail("Error: viene lanciata l'eccezione QueryEmptyException invece che QueryNullException");
        }
        catch(QueryNullException e)
        {
            //OK
        }
        catch(IOException e)
        {
            fail("Error: viene lanciata l'eccezione IOException invece che QueryNullException");
        }
        catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            fail("Error: viene lanciata l'eccezione ParseException invece che QueryNullException");
        }
    }
    
    @Test
    public void TestValidSearch()
    {
         try
        {
            TopDocs tp=this.srh.search("lucene-general");
        }
        catch(QueryEmptyException e)
        {
            fail("Error: Query empty!");
        }
        catch(QueryNullException e)
        {
            fail("Error: Query Null!");
        }
        catch(IOException e)
        {
            fail("Error: IOException in search()");
        }
        catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            fail("Error: ParseException in search()");
        } 
    }
    
    @Test
    public void TestInvalidInputSearch()
    {
        try
        {
            TopDocs tp=this.srh.search("\\");
        }
        catch(QueryEmptyException e)
        {
            fail("Error: Query empty!");
        }
        catch(QueryNullException e)
        {
            fail("Error: Query Null!");
        }
        catch(IOException e)
        {
            fail("Error: IOException in search()");
        }
        catch (org.apache.lucene.queryparser.classic.ParseException ex) {
            //OK
        } 
    }
}
