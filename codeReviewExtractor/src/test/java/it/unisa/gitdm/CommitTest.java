package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;





public class CommitTest {
	private Commit commit;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Account author=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		Account committer=new Account(3, "marco", "marco@gmail.com", "marco");

		this.commit=new Commit("commitId", "commitParent", "subjectParent", author, committer, "subject", "message");
	}

	@After
	public void tearDown() throws Exception {
		this.commit=null;
	}


	@Test
	public void testSetGetCommitId() {
		this.commit.setCommitId("newcommitid");
		assertEquals("newcommitid", this.commit.getCommitId());
	}
	
	@Test
	public void testSetGetCommitParent() {
		this.commit.setCommitParent("newcommitparent");
		assertEquals("newcommitparent", this.commit.getCommitParent());
	}
	
	@Test
	public void testSetGetSubjectParent() {
		this.commit.setSubjectParent("newsubjectParent");
		assertEquals("newsubjectParent", this.commit.getSubjectParent());
	}
	
	@Test
	public void testSetGetAuthor() {
		Account author=new Account(3, "marco", "marco@gmail.com", "marco");

		this.commit.setAuthor(author);
		assertEquals(author, this.commit.getAuthor());
	}
	
	@Test
	public void testSetGetCommitter() {
		Account committer=new Account(1, "fabio", "fabio@gmail.com", "fabio");

		this.commit.setCommitter(committer);
		assertEquals(committer, this.commit.getCommitter());		
	}

	@Test
	public void testSetGetSubject() {
		this.commit.setSubject("newsubject");
		assertEquals("newsubject", this.commit.getSubject());
	}
	
	@Test
	public void testSetGetMessage() {
		this.commit.setMessage("newMessage");
		assertEquals("newMessage", this.commit.getMessage());
	}
	
	@Test
	public void testToString() {
		Account author=new Account(1, "fabio", "fabio@gmail.com", "fabio");
		Account committer=new Account(3, "marco", "marco@gmail.com", "marco");
		
		this.commit.setAuthor(author);
		this.commit.setCommitter(committer);
		this.commit.setCommitParent("commitParent");
		this.commit.setMessage("message");
		this.commit.setSubject("subject");
		this.commit.setSubjectParent("subjectParent");
		this.commit.setCommitId("commitid");
		
		assertEquals(this.commit.toString(), this.commit.getCommitId()+";"+this.commit.getCommitParent()+";"+this.commit.getSubjectParent()+";"+this.commit.getAuthor()+";"+this.commit.getCommitter()+";"+this.commit.getSubject()+";"+this.commit.getMessage());
		
	}
}
