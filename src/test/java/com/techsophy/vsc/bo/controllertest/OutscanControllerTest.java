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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.techsophy.vsc.model.ApiErrorsResponse;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = VscBoApplication.class
		)
@TestPropertySource(locations = "classpath:vsctest.properties")
public class OutscanControllerTest {
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
	/*
	 * Outscan test cases
	 *  @Request:POST
	 */

	@Test
	public void submitOutscanAtSpokeForHubTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SH\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void submitOutscanAtSpokeForMissionTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SM\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void submitOutscanAtHubForMissionTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"HM\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void submitOutscanAtMissionForHubTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"MH\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	@Test
	public void submitOutscanAtMissionForSpokeTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"MS\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void submitOutscanAtHubForSpokeTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"HS\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	/*
	 * Outscan negative test cases
	 * @Request:POST
	 */

	@Test
	public void submitOutscanWithoutBatchTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"HS\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError()).andReturn();

	}

	@Test
	public void submitOutscanWithInvalidOperationTest() throws Exception {
		String url="/outscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"Hs\",\"comments\":\"Batch outscanned\",\"batchNumbers\":[\"1\",\"2\"]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError()).andReturn();

	}
	/*
	 * Outscan testcases to get Batch details
	 */

	@Test
	public void getBatchDetailsAtSpokeForHubTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "SH001")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getBatchDetailsAtHubForMissionTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "HM002")
				.param("operation", "HM");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getBatchDetailsAtSpokeForMissionTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "SM001")
				.param("operation", "SM");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getBatchDetailsAtHubForSpokeTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "HS001")
				.param("operation", "HS");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getBatchDetailsAtMissionForHubTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "MH001")
				.param("operation", "MH");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getBatchDetailsAtMissionForSpokeTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "MS001")
				.param("operation", "MS");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

	/*
	 * Outscan get batch details negative cases
	 */

	@Test
	public void getBatchDetailsWithInvalidBatchNoTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "LF001")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}

	@Test
	public void getBatchDetailsWithInvalidOperationTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "LF001")
				.param("operation", "SKK");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}

	@Test
	public void getBatchDetailsWithMovedEnumberOperationTest() throws Exception {
		String url = "/outscan/batches";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("batchNo", "SH002")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
}
