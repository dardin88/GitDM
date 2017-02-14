package it.unisa.gitdm;



import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




public class ChangeTest {
	private Change change;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Account owner=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		ArrayList<Account> reviewers=null;
		ArrayList<Message> messages=null;

		this.change=new Change("id", "project", "branch", "change_id", "subject", "status", "created", "updated", 1, 2, 3, owner, reviewers, messages);
	}

	@After
	public void tearDown() throws Exception {
		this.change=null;
	}


	@Test
	public void testSetGetId() {
		this.change.setId("newid");
		assertEquals("newid", this.change.getId());
	}
	
	@Test
	public void testSetGetProject() {
		this.change.setProject("newproject");
		assertEquals("newproject", this.change.getProject());
	}

	@Test
	public void testSetGetBranch() {
		this.change.setBranch("newbranch");
		assertEquals("newbranch", this.change.getBranch());
	}

	
	@Test
	public void testSetGetChangeId() {
		this.change.setChange_id("newchangeid");
		assertEquals("newchangeid", this.change.getChange_id());
	}
	
	@Test
	public void testSetGetSubject() {
		this.change.setSubject("newsubject");
		assertEquals("newsubject", this.change.getSubject());
	}
	
	@Test
	public void testSetGetStatus() {
		this.change.setStatus("newstatus");
		assertEquals("newstatus", this.change.getStatus());
	}
	
	@Test
	public void testSetGetCreated() {
		this.change.setCreated("newcreated");
		assertEquals("newcreated", this.change.getCreated());
	}
	
	
	@Test
	public void testSetGetInsertions() {
		this.change.setInsertions(4);
		assertEquals(4, this.change.getInsertions());
	}
	
	@Test
	public void testSetGetDeletions() {
		this.change.setDeletions(5);
		assertEquals(5, this.change.getDeletions());
	}
	
	@Test
	public void testSetGetNumber() {
		this.change.set_number(6);
		assertEquals(6, this.change.get_number());
	}
	
	@Test
	public void testSetGetOwner() {
		Account account =new Account(4, "marco", "marco@gmail.com", "marco");
		this.change.setOwner(account);
		assertEquals(account, this.change.getOwner());
	}
	
	@Test
	public void testSetGetReviewers() {
		Account account =new Account(4, "marco", "marco@gmail.com", "marco");
		ArrayList<Account> reviewers=new ArrayList<Account>();
		reviewers.add(account);
		this.change.setReviewers(reviewers);
		assertEquals(reviewers, this.change.getReviewers());
	}
	
	@Test
	public void testSetGetMessages() {
		Account account =new Account(4, "marco", "marco@gmail.com", "marco");
		Message message=new Message("id", account, "date", "message", 4);
		ArrayList<Message> messages=new ArrayList<Message>();
		messages.add(message);
		this.change.setMessages(messages);
		assertEquals(messages, this.change.getMessages());
	}
	
	@Test
	public void testToString() {
		Account account =new Account(4, "marco", "marco@gmail.com", "marco");
		ArrayList<Account> reviewers=new ArrayList<Account>();
		reviewers.add(account);
		this.change.setReviewers(reviewers);
		assertEquals(this.change.toString(), this.change.getId()+";"+this.change.getProject()+";"+this.change.getBranch()+";"+this.change.getChange_id()+";"+this.change.getSubject()+";"+this.change.getStatus()+";"+this.change.getCreated()+";"+this.change.getUpdated()+";"+this.change.getInsertions()+
				";"+this.change.getDeletions()+";"+this.change.get_number()+";"+this.change.getOwner()+";"+this.change.getReviewers()+";"+this.change.getMessages());
	}
	
	
}
