package it.unisa.gitdm;



import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;





	public class AccountTest {
		private Account account;
		private Account account_1;
		
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
		}

		@Before
		public void setUp() throws Exception {
			this.account=new Account(1009787, "fabio92580", "f.decicco3@gmail.com", "fabio92580");
			this.account_1=new Account("2016-11-10 13:25:24.194000000", 1009787, "fabio92580", "f.decicco3@gmail.com", "fabio92580");
		}

		@After
		public void tearDown() throws Exception {
			this.account=null;
			this.account_1=null;
		}



		@Test
		public void testSetGetRegistred() {
			this.account_1.setRegistred("2017-5-10 17:25:24.194000000");
			assertEquals("2017-5-10 17:25:24.194000000", this.account_1.getRegistred());
			
		}

		
		@Test
		public void testSetGetId() {
			this.account.setId(1234);
			assertEquals(1234, this.account.getId());
			
			
		}


		@Test
		public void testSetGetName() {
			this.account.setName("fabio");
			assertEquals("fabio", this.account.getName());
		}


		@Test
		public void testSetGetEmail() {
			this.account.setEmail("fabio@gmail.com");
			assertEquals("fabio@gmail.com", this.account.getEmail());
		}


		@Test
		public void testSetGetUsername() {
			this.account.setUsername("fabio");
			assertEquals("fabio", this.account.getUsername());
		}

		@Test
		public void testToString() {
			this.account.setRegistred("2016-11-10 13:25:24.194000000");
			this.account.setId(1009787);
			this.account.setName("fabio92580");
			this.account.setUsername("fabio92580");
			this.account.setEmail("f.decicco3@gmail.com");
			
			assertEquals(this.account.toString(), this.account.getName()+";"+this.account.getId()+";"+this.account.getEmail()+";"+this.account.getUsername()+";"+this.account.getRegistred());
		}

	}
