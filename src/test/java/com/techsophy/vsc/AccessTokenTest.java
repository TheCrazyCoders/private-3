package com.techsophy.vsc;

import javax.xml.bind.DatatypeConverter;
import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONObject;

public class AccessTokenTest {
	public static String getAccessToken() throws Exception{
		String accessToken=null;
			JSONObject jsonRequest=new JSONObject();
			jsonRequest.put("username", "sravani");
			jsonRequest.put("password", "web@123");
			jsonRequest.put("grant_type", "password");

			HttpClient httpClient = new HttpClient();
			PostMethod mPost = new PostMethod("http://172.16.0.52:8080/vsc/oauth/token");
			String name = "vsc-client";
			String password = "secret";
			String authString = name + ":" + password;
			String encoding = DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
			JSONObject jsonResponse = null;
				mPost.setRequestEntity(new StringRequestEntity(jsonRequest.toString(), "application/json", "utf-8"));
				mPost.setRequestHeader("Authorization", "Basic "+encoding);
				mPost.setRequestHeader("Content-Type", "application/json");
				int statusCode = httpClient.executeMethod(mPost);
				String resultObj = mPost.getResponseBodyAsString();
				if (statusCode == 200) {
					jsonResponse=new JSONObject(resultObj);
				}
				accessToken=jsonResponse.get("access_token").toString();
				return accessToken;
	}

}
