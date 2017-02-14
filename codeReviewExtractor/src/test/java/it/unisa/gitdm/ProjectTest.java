package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class ProjectTest {
	private Project project;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.project=new Project("id", "name", "parent", "description", "state");
	}

	@After
	public void tearDown() throws Exception {
		this.project=null;
	}


	@Test
	public void testSetGetId(){
		this.project.setId("newid");
		assertEquals("newid", this.project.getId());
	}
	
	@Test
	public void testSetGetName(){
		this.project.setName("newname");
		assertEquals("newname", this.project.getName());
	}
	
	@Test
	public void testSetGetParent(){
		this.project.setParent("newparent");
		assertEquals("newparent", this.project.getParent());
	}
	
	@Test
	public void testSetGetDescription(){
		this.project.setDescription("newdescription");
		assertEquals("newdescription", this.project.getDescription());
	}
	
	@Test
	public void testSetGetState(){
		this.project.setState("newstate");
		assertEquals("newstate", this.project.getState());
	}

	@Test
	public void testToString() {
		this.project.setDescription("description");
		this.project.setId("id");
		this.project.setName("name");
		this.project.setParent("parent");
		this.project.setState("state");
		
		assertEquals(this.project.toString(), this.project.getId()+";"+this.project.getName()+";"+this.project.getParent()+";"+this.project.getDescription()+";" + this.project.getState());
	}

}
