package services;

import common.Helper;
import common.HttpClient;
import data.Data;
import org.apache.catalina.Context;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.catalina.connector.Connector;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
@CrossOrigin
public class DataServices
{
    private Data data;
    private static final String SECRET = "82hj 36j !!!4 %$ suyseuweyb ^^8hscvb";

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(redirectConnector());
//        return tomcat;
//    }
//
//    private Connector redirectConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8096);
//        connector.setSecure(false);
//        connector.setRedirectPort(8095);
//        return connector;
//    }

    @PostMapping(path= "/")
    String home(HttpServletRequest request, HttpServletResponse response) {
        setUtf();
        if(data == null){
            data = new Data("dev","topor234");
        }
        JSONObject obj = new JSONObject();


        if ("POST".equalsIgnoreCase(request.getMethod()))
        {
            String test = "";
            try {
                test = IOUtils.toString(request.getReader());;
                obj = new JSONObject(test);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String secret = obj.getString("secret");
        String query = (String)obj.get("query");
        String type = (String)obj.get("type");
        JSONObject result = new JSONObject("{}");
        if(secret.equals(SECRET)){
            switch(type){

                case "pull-string":{
                    result.put("message",data.pullString(query));
                    break;
                }

                case "pull-array":{
                    result.put("message",data.pullArray(query));
                    break;
                }


                case "pull-list":{
                    StringBuilder builder = new StringBuilder("");
                    JSONArray array = new JSONArray();
                    List<String> l = data.pullList(query);
                    for(int i = 0; i< l.size(); i++){
                        array.put(l.get(i));
                    }
                    if(array !=null){
                        if(array.length()>0){
                            result.put("message",array);
                        }
                    }
                    //result = data.pullList(query).forEach(item -> builder.append("\"" + item + ""));
                    break;
                }
                case "pull-table":{
                    JSONArray array = data.pullTable(query);
                    result.put("message",array);
                    break;
                }
                case "pull-map":{

                    Map<String,String> map = data.pullMap(query);
                    JSONObject object = new JSONObject(map);

                    result.put("message",object);
                    //result = data.pullList(query).forEach(item -> builder.append("\"" + item + ""));
                    break;
                }
                case "execute":{
                    result.put("message",data.Execute(query));
                    break;
                }
            }

        }else{
            response.setStatus(401);
        }

        return result.toString();
    }


    @RequestMapping("/dummy")
    String homeSpare(HttpServletRequest request) {

        if(data == null){
            data = new Data("dev","topor234");
        }
        String secret = "7js dj£^ skdjsd  ds8888dssd!!!";
        String email = request.getParameter("email");


        return null;
    }

    private void setUtf() {
        try {
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] arg){
        SpringApplication.run(DataServices.class,arg);
    }
}
