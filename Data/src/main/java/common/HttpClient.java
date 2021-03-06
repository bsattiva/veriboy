package common;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClient {


    private final static CloseableHttpClient httpClient = HttpClients.createDefault();



    private void close() throws IOException {
        httpClient.close();
    }

    public static JSONObject sendGet(String url) throws Exception {

        HttpGet request = new HttpGet(url);
        String result = "";

        try (CloseableHttpResponse response = httpClient.execute(request)) {


            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {

                result = EntityUtils.toString(entity);

            }

        }

        return new JSONObject(result.replaceAll("\n","").replaceAll("\r",""));
    }

    public static JSONObject sendPost(List<NameValuePair> urlParameters ,String url) throws Exception {

        HttpPost post = new HttpPost(url);
        JSONObject result = new JSONObject("{}");
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            result.put("message",response.getEntity());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        return result;
    }




}