package org.seckill.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static String httpGet(String url) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse resp = client.execute(httpGet);
			
			HttpEntity entity = resp.getEntity();
	        String respContent = EntityUtils.toString(entity , "UTF-8").trim();
	        httpGet.abort();
	        client.getConnectionManager().shutdown();
	        return respContent;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 public static String httpPost(String url, Map<String, String> params) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
			for(Map.Entry<String, String> entry : params.entrySet()){
				NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				valuePairs.add(nameValuePair);
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "UTF-8");
			httpPost.setEntity(formEntity);
			HttpResponse resp = client.execute(httpPost);
			
			HttpEntity entity = resp.getEntity();
	        String respContent = EntityUtils.toString(entity , "UTF-8").trim();
	        httpPost.abort();
	        client.getConnectionManager().shutdown();
	        return respContent;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
	 
	 public static String getRemortIP(HttpServletRequest request)     {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip.equals("0:0:0:0:0:0:0:1")) {
				ip = "本地";
			}
			return ip;
	}
}
