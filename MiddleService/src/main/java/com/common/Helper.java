package com.common;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class Helper {
    public static final Logger LOGGER = new Logger();
    private static final String HOME = System.getProperty("user.dir");
    private static final String SEP = File.separator;

    public static JSONObject failedObject() {
        JSONObject object = new JSONObject();
        object.put("status", "failed");
        return object;
    }

    public static String getUrl(String key){
        return Helper.getStringFromProperties("config.properties",key);
    }

    public static List<String> getList(final JSONArray array) {
        List<String> list = new ArrayList<>();
        for(var i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }

        return list;
    }

    public static boolean isThing(final String value) {
        boolean result = false;
        if (value != null && value.length() > 0)
        {
            for (char ch : value.toCharArray()) {
                if (!Character.isSpaceChar(ch)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public static String getHomeDir(String[] tree){
        StringBuilder builder = new StringBuilder(HOME);
        Arrays.asList(tree).forEach(folder -> builder.append(SEP + folder));
        return builder.toString();
    }

    public static String getStringFromProperties(String file,String key){
        Properties properties = new Properties();
        String result = "";
        try {
            properties.load(new FileInputStream(file));
            result = properties.getProperty(key);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public static JSONObject getRequestBody(final HttpServletRequest request){
        JSONObject obj = new JSONObject();
        String test = "";
        String value = "";
        try {
            test = IOUtils.toString(request.getReader());
            value = test
                    .replaceAll("\r", "<r>")
                    .replaceAll("\n", "<n>")
                    .replaceAll("\t", "");
            obj = new JSONObject(value);



        } catch (Throwable e) {
             LOGGER.error(e.getMessage());
        }

        return obj;
    }

}
