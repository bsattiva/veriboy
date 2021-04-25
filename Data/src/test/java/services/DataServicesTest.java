package services;

import common.HttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataServicesTest {

    @Test
    void home() {
        String query = "select view from content.content where view='about'";
        String url = "https://localhost:8095/register";
        List<NameValuePair> urlParameters = new ArrayList<>();

        urlParameters.add(new BasicNameValuePair("query", query));
        urlParameters.add(new BasicNameValuePair("type", "pull-string"));
        JSONObject obj = new JSONObject("{}");
        try {
            obj = HttpClient.sendPost(urlParameters,url);
        } catch (Exception e) {
            obj.put("message","Something went wrong!");
        }

        Assert.assertTrue("Error",obj.get("message").equals("about"));
    }
}