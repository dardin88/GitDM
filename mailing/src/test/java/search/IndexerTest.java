/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import Exceptions.DataDirPathEmptyException;
import Exceptions.DataDirPathNullException;
import Exceptions.FileNullException;
import Exceptions.InvalidDirectoryException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.CorruptIndexException;
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
public class IndexerTest {
    
    private Indexer indx;
    
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
        catch (IOException ex)
        {
            fail("Error: IOException nel costruttore Indexer()");
        }
    }
    
    @After
    public void tearDown()
    {
        try
        {
            this.indx.close();
            this.indx=null;
        }
        catch (IOException ex)
        {
            fail("Error: IOException in close()");
        }
    }
  
    @Test
    public void TestClose() 
    {
        try
        {
            this.indx.close();
        }
        catch(CorruptIndexException e)
        {
            fail("Error: CorruptIndexException in Indexer.close()");
        }
        catch (IOException ex)
        {
            fail("Error: IOException in Indexer.close()");
        }
    }
    
    @Test
    public void TestCreateIndexDataDirPathNull()
    {
        try
        {
            this.indx.createIndex(null,new TextFileFilter());
            fail("Error: Data Directory Path NULL!");
        }
        catch (IOException ex)
        {
            fail("Error: Viene lanciata l'eccezione IOException invece che DataDirPathNullException!");
        }
        catch(DataDirPathNullException e)
        {
            //OK
        }
        catch(DataDirPathEmptyException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathEmptyException invece che DataDirPathNullException!");
        }
        catch(FileNullException e)
        {
            fail("Error: Viene lanciata l'eccezione FileNullException invece che DataDirPathNullException!");
        }
    }
    
    @Test
    public void TestCreateIndexFilterNull()
    {
        try
        {
            this.indx.createIndex("C:\\Users\\depiano.it\\Desktop\\store_messages\\messages",null);
            fail("Error: FileFilter NULL!");
        }
        catch (IOException ex)
        {
            fail("Error: Viene lanciata l'eccezione IOException invece che FileNullException!");
        }
        catch(DataDirPathNullException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathNullException invece che FileNullException!");
        }
        catch(DataDirPathEmptyException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathEmptyException invece che FileNullException!");
        }
        catch(FileNullException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestCreateIndexInvalidDataDirPath()
    {
        try
        {
            this.indx.createIndex("helloooooooooooooooo",new TextFileFilter());
            fail("Error: Invalid Data Directory Path!");
        }
        catch (IOException ex)
        {
            fail("Error: Viene lanciata l'eccezione IOException invece che InvalidDirectoryException!");
        }
        catch(InvalidDirectoryException e)
        {
            //OK
        }
        catch(DataDirPathNullException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathNullException invece che IOException!");
        }
        catch(DataDirPathEmptyException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathEmptyException invece che IOException!");
        }
        catch(FileNullException e)
        {
            fail("Error: Viene lanciata l'eccezione FileNullException invece che IOException!");
        }
    }
    
    @Test
    public void TestCreateIndexEmptydDataDirPath()
    {
        try
        {
            this.indx.createIndex("",new TextFileFilter());
            fail("Error: Empty Data Directory Path!");
        }
        catch (IOException ex)
        {
             fail("Error: Viene lanciata l'eccezione IOException invece che DataDirPathEmptyException!");
        }
        catch(DataDirPathNullException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathNullException invece che DataDirPathEmptyException!");
        }
        catch(DataDirPathEmptyException e)
        {
            //OK;
        }
        catch(FileNullException e)
        {
            fail("Error: Viene lanciata l'eccezione FileNullException invece che DatDirPathEmptyException!");
        }
    }
    
    @Test
    public void TestCreateIndexInvalidDirPath()
    {
        try
        {
            this.indx.createIndex("C:\\Users\\hello\\Desktop\\index",new TextFileFilter());
            fail("Error: Invalid Data Directory Path!");
        }
        catch (IOException ex)
        {
            fail("Error: Viene lanciata l'eccezione IOException invece che InvalidDirectoryException!");
        }
        catch(InvalidDirectoryException e)
        {
            //OK
        }
        catch(DataDirPathNullException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathNullException invece che IOException!");
        }
        catch(DataDirPathEmptyException e)
        {
            fail("Error: Viene lanciata l'eccezione DataDirPathEmptyException invece che IOException!");
        }
        catch(FileNullException e)
        {
            fail("Error: Viene lanciata l'eccezione FileNullException invece che IOException!");
        }
    }
    
    @Test
    public void TestGetDirectory()
    {
        Assert.assertNotNull(this.indx.getIndexDirectory());
    }
    
    @Test
    public void TestGetIndexerWriter()
    {
        Assert.assertNotNull(this.indx.getIndexWriter());
    }
}
