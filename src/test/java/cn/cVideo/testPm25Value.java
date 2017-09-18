package cn.cVideo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Ken Pan on 2017/4/25.
 */
public class testPm25Value {
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
    public static void main(String[] args) {
        String result = httpGet("http://www.mypm25.cn/cityAvg.action?areano=320100&bj=1");
        System.out.println(result);
    }
}
