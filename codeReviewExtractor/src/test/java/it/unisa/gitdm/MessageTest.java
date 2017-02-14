package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class MessageTest {
	private Message message;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Account account=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		this.message=new Message("id", account, "date", "message", 1);
	}

	@After
	public void tearDown() throws Exception {
		this.message=null;
	}



	@Test
	public void testSetGetId() {
		this.message.setId("newid");
		assertEquals("newid", this.message.getId());
	}
	
	
	@Test
	public void testSetGetAuthor() {
		Account account=new Account(3, "giuseppe", "giuseppe@gmail.com", "giuseppe");

		this.message.setAuthor(account);
		assertEquals(account, this.message.getAuthor());
	}
	
	@Test
	public void testSetGetDate() {
		this.message.setDate("newdate");
		assertEquals("newdate", this.message.getDate());
	}
	
	@Test
	public void testSetGetMessage() {
		this.message.setMessage("newmessage");
		assertEquals("newmessage", this.message.getMessage());
	}
	
	@Test
	public void testSetGetRevisionNumber() {
		this.message.set_revision_number(3);
		assertEquals(3, this.message.get_revision_number());
	}
	
	
	@Test
	public void testToString() {
		Account account=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		this.message.set_revision_number(1);
		this.message.setDate("date");
		this.message.setMessage("message");
		this.message.setId("id");
		this.message.setAuthor(account);
		
		assertEquals(this.message.toString(), this.message.getAuthor()+";"+this.message.getDate()+";"+this.message.get_revision_number()+";"+this.message.getId()+";"+this.message.getMessage());
	}
	
	
	
	

}
