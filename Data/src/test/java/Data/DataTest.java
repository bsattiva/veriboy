package Data;

import data.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private static final Data MYDATA = new Data("dev","topor234");
    @Test
    void pullArray() {

        JSONObject result =  MYDATA.pullArray("select col1,col2 from test.test where col1='test1'");

        Assert.assertTrue("value of test2 expected: test2 and found: " + (String)result.get("col2"),"test2".equals((String)result.get("col2")));
    }

    @Test
    void pullMapData(){
        Map<String,String> result = MYDATA.pullMap("select col1,col2 from test.test");
        Assert.assertTrue("Map size is:" + result.size(), result.size()>1);
    }

    @Test
    void pullTable(){
        JSONArray array = MYDATA.pullTable("select view,section,element,content from content.comment");
        Assert.assertTrue("array length is: " + array.length(), array.length() >0);
    }


}