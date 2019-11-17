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
public class BatchControllerTest {
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

	/*@Test
	public void attachToBatchAtSpokeForHubTest() throws Exception {
		String url="/batch";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{ \"batchNo\": \"SH004\", \"operation\": \"SH\", \"eNumbers\":[\"EZ1550466610185\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void getEnumberDetailsForBatchTest() throws Exception {
		String url = "/batch";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("eNumber", "EZ1550248189101")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
	@Test
	public void getEnumberDetailsForBatchWithInvalidEnumberTest() throws Exception {
		String url = "/batch";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("eNumber", "E1234")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	
	@Test
	public void getEnumberDetailsForBatchWithInvalidOperationTest() throws Exception {
		String url = "/batch";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("eNumber", ApplicationConstantsTest.E_NUMBER)
				.param("operation", "Ssh");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	

}
