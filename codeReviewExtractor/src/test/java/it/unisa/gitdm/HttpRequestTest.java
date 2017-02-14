package it.unisa.gitdm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpRequestTest {

	private HttpRequest httprequest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.httprequest=new HttpRequest<>();
	}

	@After
	public void tearDown() throws Exception {
		this.httprequest=null;
	}
	
	//GET ACCOUNT
	@Test
	public void testGetAccountValido() throws IOException, JSONException, ParseException {
		assertEquals("NAME;ID;EMAIL;USERNAME;REGISTERED\nfabio92580;1009787;f.decicco3@gmail.com;fabio92580;2016-11-10 13:25:24.194000000", this.httprequest.getAccount("fabio92580"));
	}
	
	@Test
	public void testGetAccountNullo() throws IOException, JSONException, ParseException {
		assertEquals("",this.httprequest.getAccount(""));
	}
	
	@Test
	public void testGetAccountLungo() throws IOException, JSONException, ParseException {
		assertEquals("",this.httprequest.getAccount("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
	}
	
	//GET PROJECT
		@Test
		public void testGetProjectValido() throws IOException, JSONException, ParseException {
			assertEquals("PROJECT ID;NAME;PARENT;DESCRIPTION;STATE\nmidonet%2Fmidonet;midonet/midonet;All-Projects;MidoNet is an open source network virtualisation system for Openstack clouds. Learn more at http://midonet.org;ACTIVE", this.httprequest.getProject("midonet%2fmidonet"));
		}
		
		@Test
		public void testGetProjectNonValido() throws IOException, JSONException, ParseException {
			assertEquals("", this.httprequest.getProject("midonet%fmidonet"));
		}
		
		@Test
		public void testGetProjectNullo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getProject(""));
		}

		@Test
		public void testGetProjectLungo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getProject("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
		}
		
		@Test
		public void testGetAccountNonValido() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getAccount("fabio"));
		}


		
		//GET CHANGE
		@Test
		public void testGetChangeValido() throws IOException, JSONException, ParseException {
			assertEquals("ID;PROJECT;BRANCH;CHANGE ID;SUBJECT;STATUS;CREATED;UPDATED;INSERTIONS;DELETIONS;NUMBER\nMirantis%2Fstepler~master~Ib5992649dc51db777579302254878d6224352ee1;Mirantis/stepler;master;Ib5992649dc51db777579302254878d6224352ee1;Add Neutron \"Check new flows added after restart OVS-agents\" test;MERGED;2016-11-22 08:25:18.317000000;2016-11-23 08:50:47.693000000;97;1;303138\n\n\n"
					+ "OWNER NAME;OWNER ID;OWNER EMAIL;OWNER USERNAME\nGeorgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;\n\n\nREVIEWERS ACCOUNT NAME;REVIEWERS ACCOUNT ID;REVIEWERS ACCOUNT EMAIL;REVIEWERS ACCOUNT USERNAME\nGeorgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;\n"
					+ "Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;\nMirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;\nGeorgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin\nSergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga\n"
					+ "Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci\n\n\nID MESSAGES;AUTHOR NAME MESSAGES;AUTHOR ID MESSAGES;AUTHOR EMAIL MESSAGES; AUTHOR USERNAME MESSAGES;DATE MESSAGES;REVISION NUMBER MESSAGES;TEXT MESSAGES\n"
					+ "9aa53dc9_372bc3ed;Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;2016-11-22 08:25:18.317000000;1;Uploaded patch set 1.\n"
					+ "9aa53dc9_77814b8f;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:05:33.198000000;1;Patch Set 1:  Build Started http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1376/\n"
					+ "9aa53dc9_374aa384;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:06:23.926000000;1;Patch Set 1: Code-Review-1  Build Failed   http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1376/ : FAILURE\n"
					+ "9aa53dc9_d224a5b0;Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;2016-11-22 09:17:42.564000000;1;Patch Set 1:  recheck\n"
					+ "9aa53dc9_b23a41cc;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:17:53.583000000;1;Patch Set 1: -Code-Review  Build Started http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1381/\n"
					+ "9aa53dc9_5231b5eb;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:18:41.893000000;1;Patch Set 1: Code-Review+1  Build Successful   http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1381/ : SUCCESS\n"
					+ "9aa53dc9_d2802549;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-22 09:24:49.504000000;1;Patch Set 1: Code-Review-1  (1 comment)\n"
					+ "9aa53dc9_b2dda172;Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;2016-11-22 09:26:53.562000000;1;Patch Set 1:  (1 comment)\n"
					+ "9aa53dc9_12d56d42;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-22 09:31:28.934000000;1;Patch Set 1:  (1 comment)\n"
					+ "9aa53dc9_921dfd11;Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;2016-11-22 09:51:59.566000000;2;Uploaded patch set 2.\n"
					+ "9aa53dc9_521775ed;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:52:08.629000000;2;Patch Set 2:  Build Started http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1385/\n"
					+ "9aa53dc9_32d3d13a;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-22 09:52:41.079000000;2;Patch Set 2: Code-Review+1 Verified+1\n"
					+ "9aa53dc9_d2e14500;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-22 09:53:02.038000000;2;Patch Set 2: Code-Review+1  Build Successful   http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1385/ : SUCCESS\n"
					+ "9aa53dc9_d726d215;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-22 18:13:14.342000000;2;Patch Set 2: Code-Review+2\n"
					+ "9aa53dc9_c5c4dc93;Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;2016-11-23 07:04:40.878000000;3;Uploaded patch set 3.\n"
					+ "9aa53dc9_a5c1d882;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-23 07:04:50.594000000;3;Patch Set 3:  Build Started http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1425/\n"
					+ "9aa53dc9_85bed4fd;Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci;2016-11-23 07:05:41.633000000;3;Patch Set 3: Code-Review+1  Build Successful   http://srv99-bud.infra.mirantis.net:8080/job/check-stepler/1425/ : SUCCESS\n"
					+ "9aa53dc9_25302856;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-23 08:50:45.411000000;3;Patch Set 3: Code-Review+2 Verified+1\n"
					+ "9aa53dc9_05352445;Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;2016-11-23 08:50:47.693000000;3;Change has been successfully merged by Sergei Chipiga\n", this.httprequest.getChange("303138"));
		}
	
		@Test
		public void testGetChangeNonValido()throws IOException, JSONException, ParseException{
			assertEquals("",this.httprequest.getChange("123456"));
		}
		@Test
		public void testGetChangeNullo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getChange(""));
		}

		@Test
		public void testGetChangeLungo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getChange("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
		}
		
		//GET Comment
		@Test
		public void testGetCommentValido() throws IOException, JSONException, ParseException {
			assertEquals("AUTHOR NAME;AUTHOR ID;AUTHOR EMAIL;AUTHOR USERNAME;PATCH_SET;ID;LINE;RANGE START LINE;RANGE START CHARACTER;RANGE END LINE;RANGE END CHARACTER;UPDATED;MESSAGE\nSergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;1;9aa53dc9_32d07153;300;298;0;300;70;2016-11-22 09:31:28.934000000;1. Ok, let be ``not_contain=cookies``  2. Yep, technically it's allowed. But we saw a precedent with Sofiia patch, when it leads to many asserts in test and unreadable code. As I remember, Maxim Shalamov talked about such patch with you too. Soon I will add a patch to ban matchers in tests.\n"
					+ "Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga;1;9aa53dc9_92acddee;300;298;0;300;70;2016-11-22 09:24:49.504000000;Would like to see next variant:        os_faults_steps.check_ovs_flow_cookies(compute_node, contains=cookies)\n"
					+ "Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin;1;9aa53dc9_f2f20906;300;298;0;300;70;2016-11-22 09:26:53.562000000;It should no contains. And asserts are allowed in test, aren't it?\n", this.httprequest.getComments("303138"));
		}
		
		@Test
		public void testGetCommentNonValido() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getComments("123456"));
		}
		
		@Test
		public void testGetCommentNullo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getComments(""));
		}

		@Test
		public void testGetCommentLungo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getComments("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
		}
		
		//GET REVIEWERS
		@Test
		public void testGetReviewersValido() throws IOException, JSONException, ParseException {
			assertEquals("NAME ACCOUNT;ACCOUNT ID;ACCOUNT EMAIL;ACCOUNT USERNAME\n"
			+ "Georgy Dyuldin;1008201;gdyuldin@mirantis.com;gdyuldin\n"
			+ "Sergei Chipiga;1008272;schipiga@mirantis.com;sergeychipiga\n"
			+ "Mirantis Jenkins;1009584;qa-stepler-ci@mirantis.com;qa-stepler-ci\n", this.httprequest.getReviewers("303138"));
		}
				
		@Test
		public void testGetReviewersNonValido() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getReviewers(""));
		}
		
		@Test
			public void testGetReviewersNullo() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getReviewers(""));
			}

		@Test
		public void testGetReviewersLungo() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getReviewers("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
		}
		
		//GET COMMIT
		@Test
		public void testGetCommitValido() throws IOException, JSONException, ParseException {
			assertEquals("COMMIT ID;COMMIT PARENT;SUBJECT PARENT;AUTHOR NAME;AUTHOR EMAIL;COMMITTER NAME;COMMITTER EMAIL;SUBJECT;MESSAGE\n"
					+ "f1c858b7178615bf3014631c1eaf32465469d4b3;Fix: Incorrect gateway field in local routes;Adrian Serrano;adrian@midokura.com;Adrian Serrano;adrian@midokura.com;C3PO: getHostIdByName fails if name is not unique;C3PO: getHostIdByName fails if name is not unique  This patch alters the behavior of PortMapper getHostByName which until now will silently return the first host found when a given name is used by more than one host. Now an exception will be thrown.  Ref: MI-790  Change-Id: I3661f4782eda6e467dd3248093d9efa9688ee881 Signed-off-by: Adrian Serrano <adrian@midokura.com> \n", this.httprequest.getCommit("303621","f813b2d3f57f97f0fb57a8b78eb69e609c58263e"));
		}
			
		@Test
		public void testGetCommitNonValido() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getCommit("123456","f813b2d3f57f97f0fb57a8b78eb69e609c58263e"));
		}
		
		@Test
			public void testGetCommitNullo() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getCommit("","f813b2d3f57f97f0fb57a8b78eb69e609c58263e"));
		}

		@Test
			public void testGetCommitLungo() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getCommit("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada","f813b2d3f57f97f0fb57a8b78eb69e609c58263e"));
		}
		
		@Test
		public void testGetCommitNonValido2() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getCommit("303621","f813b2d3f57f97f0fb57a8b7asd69e609c58263e"));
		}
		
		@Test
			public void testGetCommitNullo2() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getCommit("303621",""));
		}

		@Test
			public void testGetCommitLungo2() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getCommit("303621","f813b2d3f57f97f0fb5asddddddddddddddddddddddddddddaaaaaaaaaaaaaasdddddddddddddddddddddddda7a8b78eb69e609c58263e"));
		}
		
		
		//GET BRANCHES
		@Test
		public void testGetBranchesValido() throws IOException, JSONException, ParseException {
			assertEquals("REF;REVISION\nHEAD;master\nrefs/heads/backport-v50;b6d0fcd1ad435a6b664990973e9faa46ff8e4255\n"
					+ "refs/heads/hotfix/mi1404-hf0;f8171e435f7d9b3ced22d231102f310a3852586c\n"
					+ "refs/heads/hotfix/v5.0.1-hf0;e827de9e1732b80b5be04bda7df5bc1f7f37cb90\n"
					+ "refs/heads/hotfix/v5.0.2-hf0;11e6ce880d4adc0ede1759afced8c7a4b450c1aa\n"
					+ "refs/heads/hotfix/v5.0.2-hf1;18e28376d4516a5d392e3918f0b88583560f240b\n"
					+ "refs/heads/hotfix/v5.0.2-hf2;924570ce84202013a1245123f9617ce92fa5f2f6\n"
					+ "refs/heads/hotfix/v5.2.1-hf0;40b55e241800e0d211b09f80d6f8369ec776d836\n"
					+ "refs/heads/hotfix/v5.2.1-hf1;5e96567f50d17e6282a190c2c22483e4de10b3a8\n"
					+ "refs/heads/hotfix/v5.2.1-hf2;1ebfb4488828284e751c4eefba0f5a8e2efe2cc5\n"
					+ "refs/heads/jenkins-test;a137680c746e5ac345d32c54c6049dd2711379b8\n"
					+ "refs/heads/master;9df817e73675a92eb86a167116a729ede8119cb7\n"
					+ "refs/heads/stable/v5.0.1;db1eba70887195114aa4d60d536af7adbbfe6ccd\n"
					+ "refs/heads/stable/v5.0.3;a20785dd60f601ccf6acc240f3a2f776c9a429b7\n"
					+ "refs/heads/stable/v5.1.0;ad384b9124b110d05657bf1c934e2fbf67a8ce6b\n"
					+ "refs/heads/stable/v5.1.1;6bbc2fd189ec25a5eac4f45f2a72fa20b094ee86\n"
					+ "refs/heads/stable/v5.2.0;9c1749b284b60efa7d11444ef8bd67b948b5b517\n"
					+ "refs/heads/stable/v5.2.1;fcdfa839d69a00cea39e9d4a06d9079562b45437\n"
					+ "refs/heads/stable/v5.4.0;9ff392297356138bd3a139369d017b985b9523ac\n"
					+ "refs/heads/staging/v2015.06;bdf739d7bcdecf4af1b43f0f7ee83329a003f008\n"
					+ "refs/heads/staging/v5.0;ef293828e05da2f3e5c5063a6f8108a8f8dd4ff3\n"
					+ "refs/heads/staging/v5.1;e700814921bcf4eff2e4e286eef0d624f12a9134\n"
					+ "refs/heads/staging/v5.2;7f39511d550ba044dfae53522d4d4f7f9e07029e\n"
					+ "refs/heads/staging/v5.4;4e9527b5eafcaf3fa0b2117b91b49fc1f73a1ddf\n", this.httprequest.getBranches("midonet%2fmidonet"));
		}
			
		@Test
		public void testGetBranchesNonValido() throws IOException, JSONException, ParseException {
			assertEquals("",this.httprequest.getBranches(""));
	}
		
		@Test
			public void testGetBranchesNullo() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getBranches(""));
		}

		@Test
			public void testGetBranchesLungo() throws IOException, JSONException, ParseException {
				assertEquals("",this.httprequest.getBranches("fabioadadadasdadadasdadadadadadadasdasdadadadasdadadadadadada"));
		}

}
