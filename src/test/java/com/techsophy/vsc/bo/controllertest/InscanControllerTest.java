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

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = VscBoApplication.class
		)
@TestPropertySource(locations = "classpath:vsctest.properties")
public class InscanControllerTest {
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
/*	@Test
	public void getEnumberDetailsAtSpokeFromFOTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ15504761015523")
				.param("operation", "FS");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtSpokeFromHubTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1550262438379")
				.param("operation", "HS");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtSpokeFromMissionTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1550205973145")
				.param("operation", "MS");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtHubFromSpokeTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1548780670008")
				.param("operation", "SH");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtHubFromMissionTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1550248189101")
				.param("operation", "MH");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtMissionFromHubTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1550253928734")
				.param("operation", "HM");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
/*	@Test
	public void getEnumberDetailsAtMissionFromSpokeTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "EZ1550260330671")
				.param("operation", "SM");
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}*/
	
	/*
	 * Save Inscan E-No details 
	 */
	@Test
	public void saveEnumberDetailsAtSpokeFromFOTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"FS\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtSpokeFromHubTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SH\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtSpokeFromMissionTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SM\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtHubFromSpokeTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SH\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtHubFromMissionTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"MH\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtMissionFromHubTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"MH\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void saveEnumberDetailsAtMissionFromSpokeTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"SM\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	}
	
	/*
	 * Inscan Negative test cases to get E-no details
	 * 
	 */
	
	@Test
	public void saveEnumberDetailsAtSpokeFromHubWithoutOperationTest() throws Exception {
		String url="/inscans";
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url)
				.content("{\"operation\":\"\",\"eNumbers\":[{\"eNumber\":\"EZ1550269346075\",\"status\":\"\"}]}")
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	
	@Test
	public void getENumberDetailsWithScanedENoTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "E1234")
				.param("operation", "SM");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	
	@Test
	public void getENumberDetailsWithInvalidENoTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "E12344467567")
				.param("operation", "SM");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	
	@Test
	public void getENumberDetailsWithInvalidOperationTest() throws Exception {
		String url = "/inscans";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken)
				.param("searchType", "eNumber")
				.param("searchValue", "E12344467567")
				.param("operation", "Sm");
		mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();
	}
	
	
	@Test
	public void getVisaStatusListTest() throws Exception {
		String url = "/inscans/masters";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.param("access_token", accessToken);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}
	

}
