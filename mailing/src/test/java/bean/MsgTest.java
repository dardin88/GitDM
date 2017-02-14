/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.GregorianCalendar;
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
public class MsgTest {
    
    private Msg message;
    private Msg message_1;
  
    public MsgTest()
    {
        
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
        this.message=new Msg();
        this.message_1=new Msg("06/12/2016","1234","depianoantonio@gmail.com","general@lucene.it","Project x","This is a content!","124598985");
    }
    
    @After
    public void tearDown()
    {
        this.message=null;
        this.message_1=null;
    }

    @Test
    public void testSetGetContent()
    {
        this.message.setContent("this is a content!");
        assertEquals("this is a content!",this.message.getContent());
        
        this.message.setContent(null);
        Assert.assertNull(this.message.getContent());  
        
        Assert.assertEquals("This is a content!",this.message_1.getContent());
    }
    
    @Test
    public void testSetGetMessageId()
    {
        this.message.setMessage_id("0100");
        Assert.assertEquals("0100",this.message.getMessage_id());
        
        this.message.setMessage_id(null);
        Assert.assertNull(this.message.getMessage_id());
        
        Assert.assertEquals("1234", this.message_1.getMessage_id());
    }
    
    @Test
    public void testSetGetFrom()
    {
        this.message.setFrom("depianoantonio@gmail.com");
        assertEquals("depianoantonio@gmail.com",this.message.getFrom());
        
        this.message.setFrom(null);
        Assert.assertNull(this.message.getFrom());
        
        Assert.assertEquals("depianoantonio@gmail.com", this.message_1.getFrom());
    }
    
    @Test
    public void testSetGetDate()
    {
        this.message.setDate("12/10/2016");
        assertEquals("12/10/2016",this.message.getDate());
        
        this.message.setDate(null);
        Assert.assertNull(this.message.getDate());
        
        this.message.setDate("Wed Oct  1 02:02:52 2014");
        assertEquals("Wed Oct  1 02:02:52 2014",this.message.getDate()); 

        this.message.setDate("");
        Assert.assertNotSame("Format date: String",this.message.getDate(),new GregorianCalendar());
        
        Assert.assertEquals("06/12/2016", this.message_1.getDate());
    }
    
    @Test
    public void testSetGetTo()
    {
        this.message.setTo("depianoantonio@gmail.com");
        assertEquals("depianoantonio@gmail.com",this.message.getTo());
        
        this.message.setTo(null);
        Assert.assertNull(this.message.getTo());   
        
        Assert.assertEquals("general@lucene.it", this.message_1.getTo());
    }
    
   @Test
   public void testSetGetSubject()
    {
        this.message.setSubject("Project x");
        assertEquals("Project x",this.message.getSubject());
        
        this.message.setSubject(null);
        Assert.assertNull(this.message.getSubject());  
        
        Assert.assertEquals("Project x", this.message_1.getSubject());
    }
    
    @Test
    public void testSetGetInReplyTO()
    {
        this.message.setInReplyTO("154564545");
        assertEquals("154564545",this.message.getInReplyTO());
        
        this.message.setInReplyTO(null);
        Assert.assertNull(this.message.getInReplyTO()); 
        
        Assert.assertEquals("124598985", this.message_1.getInReplyTO());
    }
    
    @Test
    public void testToString()
    {
        this.message.setContent("This is a content!");
        this.message.setDate("06/12/2016");
        this.message.setFrom("depianoantonio@gmail.com");
        this.message.setInReplyTO("101010");
        this.message.setSubject("Project x");
        this.message.setTo("general@lucene.it");
        
        Assert.assertEquals(this.message.toString(),"To: "+this.message.getTo()+"\ndate: "+this.message.getDate()+"\nmessage id: "+this.message.getMessage_id()+
                        "\nsubject: "+this.message.getSubject()+"\nin-Reply-TO: "+this.message.getInReplyTO()+"\ncontent: "+this.message.getContent());
    }
    
    @Test
    public void testAppendToContent()
    {
        this.message.appendToContent("Hello!");
        Assert.assertEquals("Hello!\n",this.message.getContent());
    }
    
}
