package com.jt.test.httpClient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestHttpClient {
	
	@Test
	public void testGet() throws ClientProtocolException, IOException{
		
		CloseableHttpClient httpClient  = HttpClients.createDefault();
		String uri = "http://item.jd.com/806876.html";
		HttpGet get = new HttpGet(uri);
		HttpPost httpPost = new HttpPost(uri);
		CloseableHttpResponse response =httpClient.execute(httpPost);
		
		//判断请求是否有效
		if(response.getStatusLine().getStatusCode() == 200){
			System.out.println("请求成功!!!");
			
			System.out.println(EntityUtils.toString(response.getEntity()));
		}	
	}
}
