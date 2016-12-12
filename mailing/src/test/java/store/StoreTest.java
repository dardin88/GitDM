/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import Exceptions.InputEmptySaveMsgException;
import Exceptions.InvalidMonthException;
import Exceptions.InvalidYearException;
import Exceptions.PathFileNullException;
import Exceptions.PathsFilesZeroException;
import Exceptions.ProjectNameNullException;
import Exceptions.SaveMsgNullException;
import bean.Msg;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author depiano.it
 */
public class StoreTest{
    
    private Store st;
    
    public StoreTest()
    {
        
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        this.st=new Store("lucene-general",2010,10,2010,12);
    }
    
    @After
    public void tearDown()
    {
        this.st=null;
    }

    @Test
    public void TestProjectNameNull()
    {
        try
        {
           this.st=new Store(null,2010,10,2010,12);
           fail("Permette un project-name nullo!");
        }
        catch(ProjectNameNullException e)
        {
            //OK    
        }
    }
    
    @Test
    public void TestIntervallFromYear()
    {
        try
        {
            this.st=new Store("lucene-general",-1,10,2011,04);
            fail("Permette from-year non valido!");
            
            this.st=new Store("lucene-general",2020,10,2011,04);
            fail("Permette from-year non valido!");
        }
        catch(InvalidYearException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestIntervallAtYear()
    {
        try
        {
            this.st=new Store("lucene-general",2010,10,1,04);
            fail("Permette at-year non valido!");
            
            this.st=new Store("lucene-general",2010,10,-2010,04);
            fail("Permette at-year non valido!");
            
            this.st=new Store("lucene-general",2010,10,2010,04);
            fail("Permette at-year non valido!");
        }
        catch(InvalidYearException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestIntervallFromMonth()
    {
        try
        {
            this.st=new Store("lucene-general",2010,-1,2016,04);
            fail("Permette from-month non valido!");
            
            this.st=new Store("lucene-general",2010,22,2016,04);
            fail("Permette from-month non valido!");
        }
        catch(InvalidMonthException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestIntervallAtMonth()
    {
        try
        {
            this.st=new Store("lucene-general",2010,10,2016,-1);
            fail("Permette at-month non valido!");
            
            this.st=new Store("lucene-general",2010,10,2016,22);
            fail("Permette at-month non valido!");
        }
        catch(InvalidMonthException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestIntervallFromMonthAtMonth()
    {
        try
        {
            this.st=new Store("lucene-general",2010,10,2010,-1);
            fail("Permette un intervallo di mesi non valido, sullo stesso anno!");
            
            this.st=new Store("lucene-general",2010,10,2010,04);
            fail("Permette un intervallo di mesi non valido, sullo stesso anno!");
        }
        catch(InvalidMonthException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestCreateDirStoretMsg()
    {
        File dir_store_msg=new File("C:\\Users\\depiano.it\\Desktop\\store_messages");
        Assert.assertEquals(dir_store_msg,this.st.getDirStore().toFile());
        
        Assert.assertNotNull(this.st.getDirStore().toFile());
        
        if(!dir_store_msg.exists())
            fail("Non è stata creata la directory:"+dir_store_msg.getAbsolutePath());
    }
    
    @Test
    public void TestCreateDirOutput()
    {
        File dir_output_msg=new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\output");
        Assert.assertEquals(dir_output_msg,this.st.getDirOutput().toFile());
        
        Assert.assertNotNull(this.st.getDirOutput().toFile());
        
        if(!dir_output_msg.exists())
            fail("Non è stata creata la directory:"+dir_output_msg.getAbsolutePath());
    }
    
    @Test
    public void TestCreateDirSplitMsg()
    {
        File dir_split_msg=new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\messages");
        Assert.assertEquals(dir_split_msg,this.st.getDirSplitMsg().toFile());
        
        Assert.assertNotNull(this.st.getDirSplitMsg().toFile());
        
        if(!dir_split_msg.exists())
            fail("Non è stata creata la directory:"+dir_split_msg.getAbsolutePath());
    } 
    
    @Test
    public void TestDownloads()
    {
        this.st=new SplitMsgStub("lucene-general",2010,10,2010,12);
        if(!this.st.download())
            fail("Downloads fallito!");
        
        File dir_store_msg=new File("C:\\Users\\depiano.it\\Desktop\\store_messages");
        Assert.assertEquals(dir_store_msg,this.st.getDirStore().toFile());
        
        if(this.st.getDirStore().toFile().listFiles().length<2)
           fail("Error: Non sono state create tutte le directory!");
    }
    
    @Test
    public void TestSplitMsg()
    {
        this.st=new DownloadMsgStub("lucene-general",2010,10,2010,12);
        if(!this.st.download())
                fail("Error in download file!");
     
    }
  
    
    @Test
    public void TestNullAnalyzer()
    {
        try
        {
            this.st.analyzer(null);
            fail("Permette di analizzare un oggetto null!");
        }
        catch(PathFileNullException e)
        {
            //OK
        } catch (IOException ex)
        {
            fail("Lancia un eccezione errata! Attesa eccezione PathFileNullException e viene lanciata eccezione IOException");
        }
    }
    
    @Test
    public void TestInvalidInputAnalyzer()
    {
        try
        {
            this.st.analyzer(new ArrayList<String>());
            fail("Permette di analizzare un Input non valido!");
        }
        catch(PathsFilesZeroException e)
        {
            //OK
        } catch (IOException ex)
        {
            fail("Lancia un eccezione errata! Attesa eccezione PathsFilesZeroException e viene lanciata eccezione IOException");
        }
    }
    
    @Test
    public void TestInvalidPathFileAnalyzer()
    {
        try
        {
            ArrayList<String> ar=new ArrayList<String>();
            ar.add("*.txt");
            ar.add("*.*");
            this.st.analyzer(ar);
            fail("Permette di analizzare path file non validi!");
        }
        catch(IOException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestInvalidFileAnalyzer()
    {
        try
        {
            ArrayList<String> ar=new ArrayList<String>();
            ar.add("/usr/games:/home/utente/bin");
            ar.add("\"C:\\\\trash\\\\blah\\\\blah");
            this.st.analyzer((ArrayList<String>)ar);
            fail("Permette di analizzare path file non validi!");
        }
        catch(IOException e)
        {
            //OK
        }
    }
    
    @Test
    public void TestSaveMsgNull()
    {
        try
        {
            this.st.saveMsgs(null);
            fail("Permette il salvataggio di oggetti nulli!");
        }
        catch(SaveMsgNullException e)
        {
            //OK
        }
        catch (IOException ex)
        {
            fail("Lancia un'eccezione errata! Attesa eccezione SaveMsgNullException e viene lanciata eccezione IOException");
        }
    }
    
    @Test
    public void TestInvalidInputSaveMsg()
    {
        try
        {
            this.st.saveMsgs(new ArrayList<Msg>());
            fail("Permette il salvataggio di un input non valido!");
        }
        catch(InputEmptySaveMsgException e)
        {
            //OK
        }
        catch (IOException ex)
        {
            fail("Lancia un'eccezione errata! Attesa eccezione InputEmptySaveMsgException e viene lanciata eccezione IOException");
        }
    }
    
    @Test
    public void TestWriteFileSaveMsg()
    {
        try
        {
            ArrayList<Msg> list_msg=new ArrayList<Msg>();
            list_msg.add(new Msg());
            list_msg.add(new Msg());
            list_msg.add(new Msg());
            this.st.saveMsgs(list_msg);
            if(this.st.getDirOutput().toFile().listFiles().length==0)
                fail("Error: impossibe write file");
        }
        catch(IOException e)
        {
            fail("Error in SaveMsg() when writes file objects!");
        }
    }
    
    @Test
    public void TestGetDirOutput()
    {
        Assert.assertEquals(this.st.getDirOutput().toFile(),new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\output"));
    }
    
    @Test
    public void TestGetDirStore()
    {
        Assert.assertEquals(this.st.getDirStore().toFile(),new File("C:\\Users\\depiano.it\\Desktop\\store_messages"));
    }
    
    @Test
    public void TestGetDirSplitMsg()
    {
        Assert.assertEquals(this.st.getDirSplitMsg().toFile(), new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\messages"));
    }
}
