package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.Test;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class BranchTest {
	private Branch branch;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.branch=new Branch("refs/heads/backport-v50" , "b6d0fcd1ad435a6b664990973e9faa46ff8e4255");
	}

	@After
	public void tearDown() throws Exception {
		this.branch=null;
	}

	@Test
	public void testSetGetRef() {
		this.branch.setRef("refs/heads/hotfix/mi1404-hf0");
		assertEquals("refs/heads/hotfix/mi1404-hf0", this.branch.getRef());
		
	}
	
	@Test
	public void testSetGetRevision() {
		this.branch.setRevision("f8171e435f7d9b3ced22d231102f310a3852586c");
		assertEquals("f8171e435f7d9b3ced22d231102f310a3852586c", this.branch.getRevision());
	}
	
	@Test
	public void testToString() {
	this.branch.setRef("refs/heads/backport-v50");
	this.branch.setRevision("b6d0fcd1ad435a6b664990973e9faa46ff8e4255");
	
	assertEquals(this.branch.toString(), this.branch.getRef()+";"+this.branch.getRevision());
	}

}


