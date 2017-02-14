package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class CommentTest {
	private Comment comment;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Account author=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		Range range=new Range(298, 0, 300, 70);
		this.comment=new Comment(author, 1, "id", 2, range, "updated", "message");
	}

	@After
	public void tearDown() throws Exception {
		this.comment=null;
	}


	@Test
	public void testSetGetAuthor() {
		Account account=new Account(3, "giuseppe", "giuseppe@gmail.com", "giuseppe");
		this.comment.setAuthor(account);
		assertEquals(account, comment.getAuthor());
	}
	
	@Test
	public void testSetGetPatchSet() {
		this.comment.setPatch_set(3);
		assertEquals(3, comment.getPatch_set());
	}
	
	@Test
	public void testSetGetId() {
		this.comment.setId("newid");
		assertEquals("newid", comment.getId());
	}
	
	@Test
	public void testSetGetLine() {
		this.comment.setLine(3);
		assertEquals(3, comment.getLine());
	}
	
	@Test
	public void testSetGetRange() {
		Range range=new Range(245, 23, 370, 71);
		this.comment.setRange(range);
		assertEquals(range, comment.getRange());
	}
	
	@Test
	public void testSetGetUpdated() {
		this.comment.setUpdated("newupdated");
		assertEquals("newupdated", comment.getUpdated());
	}

	@Test
	public void testSetGetMessage() {
		this.comment.setMessage("newmessage");
		assertEquals("newmessage", comment.getMessage());
	}
	
	@Test
	public void testToString() {
		Account author=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		Range range=new Range(298, 0, 300, 70);
		this.comment.setAuthor(author);
		this.comment.setRange(range);
		this.comment.setLine(23);
		this.comment.setUpdated("updated");
		this.comment.setId("id");
		this.comment.setMessage("message");
		this.comment.setPatch_set(3);
		this.comment.setId("id");
		
		assertEquals(this.comment.toString(), this.comment.getAuthor()+";"+this.comment.getPatch_set()+";"+this.comment.getId()+";"+this.comment.getRange()+";"+this.comment.getUpdated()+";"+this.comment.getMessage());
	}
}
