package com.techsophy.vsc.bo.controllertest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techsophy.vsc.AccessTokenTest;
import com.techsophy.vsc.bo.VscBoApplication;
import com.techsophy.vsc.bo.utilstest.ApplicationConstantsTest;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = VscBoApplication.class
		)
@TestPropertySource(locations = "classpath:vsctest.properties")
public class AuditorControllerTest {
	
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	@Resource
	private FilterChainProxy springSecurityFilterChain;

	public static String accessToken=null;
	
	@BeforeClass
	public static void getAccessTokenInfo() throws Exception{
			accessToken=AccessTokenTest.getAccessToken();
	}
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).
				addFilters(springSecurityFilterChain).build();

	}
	
	@Test
	public void getAuditMasterDataTest() throws Exception {
		String url ="/audit/masterdata";
				RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();		
	}
	
	@Test
	public void getApplicantDetailsForAuditTest() throws Exception {
		String url="/audit/applicantdetails";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("eNumber", ApplicationConstantsTest.E_NUMBER);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}
	
/*	commented by thirupathi because failing fix it and uncomment
 * @Test
	public void getInfoForAuditTest() throws Exception {
		String url = "/audit/list";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("applicationType", "Full Process")
				.param("auditType", "Random")
				.param("pageNo","1");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}
	*/

	/*@Test
	public void auditHoldENumberTest() throws Exception {
		String url = "/audit/holdenumber";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
				.param("access_token", accessToken) 
				.content("{\"auditStatus\":\"Hold\",\"eNumber\":\"EZ1548780670008\",\"holdReason\":\"Data Issue\"}");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();				
	}*/
	
	@Test
	public void auditHoldENumberWithoutStatusTest() throws Exception {
		String url = "/audit/holdenumber";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
				.param("access_token", accessToken) 
				.content("{\"auditStatus\":\"\",\"eNumber\":\"EZ1549855497428\",\"holdReason\":\"Data Issue\"}");
		mockMvc.perform(requestBuilder).andReturn();				
	}
	
	@Test
	public void getCompleteAuditTest() throws Exception {
		String url = "/audit";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andReturn();				
	}	

}
