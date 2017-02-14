package it.unisa.gitdm;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class RangeTest {
	private Range range;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.range=new Range(298, 0, 300, 70);
	}

	@After
	public void tearDown() throws Exception {
		this.range=null;
	}


	@Test
	public void testSetGetStartLine() {
		this.range.setStart_line(1);
		assertEquals(1, this.range.getStart_line());
	}
	
	@Test
	public void testSetGetEndLine() {
		this.range.setEnd_line(1);
		assertEquals(1, this.range.getEnd_line());
	}
	
	@Test
	public void testSetGetStartCharacter() {
		this.range.setStart_character(1);
		assertEquals(1, this.range.getStart_character());
	}
	
	@Test
	public void testSetGetEndCharacter() {
		this.range.setEnd_character(1);
		assertEquals(1, this.range.getEnd_character());
	}
	
	@Test
	public void testToString() {
		this.range.setStart_line(298);
		this.range.setEnd_character(70);
		this.range.setStart_character(0);
		this.range.setEnd_line(300);
		
		assertEquals(this.range.toString(), this.range.getStart_line()+";"+this.range.getStart_character()+";"+this.range.getEnd_line()+";"+this.range.getEnd_character());
	}
}
