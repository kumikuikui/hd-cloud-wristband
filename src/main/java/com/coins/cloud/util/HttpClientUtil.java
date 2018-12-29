package com.coins.cloud.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	// 初始化HttpClient
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 
	 * @Title: executeByPost
	 * @param:
	 * @Description: POST方式访问
	 * @return String
	 */
	public static String executeByPost(String url, String json) {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=utf-8");
		String responseJson = null;
		try {
			System.out.println("###################################"+json);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity httpEntity = response.getEntity();
			responseJson = EntityUtils.toString(httpEntity, "utf-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
			httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
		}
		return responseJson;
	}

	/**
	 * 
	 * @Title: executeByGet
	 * @param:
	 * @Description: get请求
	 * @return String
	 */
	public static String executeByGet(String url, Object[] params) {
		String messages = MessageFormat.format(url, params);
		HttpGet get = new HttpGet(messages);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseJson = null;
		try {
			responseJson = httpClient.execute(get, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
			httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
		}
		return responseJson;
	}
}
