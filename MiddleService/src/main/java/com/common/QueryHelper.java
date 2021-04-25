package com.common;

import com.common.Helper;
import com.common.HttpClient;
import com.common.Logger;
import org.json.JSONObject;

import java.util.HashMap;

public class QueryHelper {
    private static final String SECRET = "82hj 36j !!!4 %$ suyseuweyb ^^8hscvb";
    private static final String MAIL_SECRET = "qdb82qjd!^&shaagsa ashjjsag &^(";
    private static final Logger LOGGER = new Logger();
    public static JSONObject getData(final String query,final String type){
        JSONObject object = new JSONObject("{}");
        object.put("query",query);
        object.put("type",type);
        object.put("secret",SECRET);
        String url = Helper.getStringFromProperties(Helper.getHomeDir(new String[]{"config.properties"}),"data.url");
        return HttpClient.sendHttpsPost(object,url);
    }

    public static JSONObject getProfile(final String token){
        JSONObject result = Helper.failedObject();
        String url = Helper.getUrl("auth.url") + "?token=" + token + "&scope=";
        try {
            result = HttpClient.sendGet(url,new HashMap<>()).getJSONObject("profile");
            result.remove("seed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getIdByName(final String name){
        String result = "";
        String url = Helper.getUrl("user.url") + "user-id?name=" + name;
        try {
            result = HttpClient.sendGet(url,new HashMap<>()).getString("message");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject sendMail(String template,String email, String subject,String replace){
        JSONObject result = Helper.failedObject();
        JSONObject data = new JSONObject();
        data.put("template",template);
        data.put("email",email);
        data.put("subject",subject);
        data.put("subject",replace);
        String url = Helper.getUrl("mail.url");
        result = HttpClient.sendHttpsPost(data,url);
        return result;
    }

}
