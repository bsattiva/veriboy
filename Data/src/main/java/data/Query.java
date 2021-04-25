package data;


import common.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bsati
 */
public class Query{
    boolean isconcat = false;
    String type;
    String isel;
    String id;
    String boss;
    Map<String, String> parameters;

    String[] schemas;
    String[] tables;

    String[] columns;
    String[] fields;
    String[] values;
    String[] orders;
    public String content_type;
    public String query;
    public String signature;

    private String qr = "select columns from schemas.tables where conditions order by numbers";

    public Query()
    {
        parameters = new HashMap<>();
    }



    void handleConcat(String col)
    {
        String regex = "^concat\\((.*)\\)";
        String cl = Helper.getFromPattern(regex, col);
        if(Helper.isThing(cl))
        {
            columns = cl.split(",");
            isconcat = true;
        }

    }

    public Query(String q)
    {
        query = q;
        populateQuery();
    }



    private void populateQuery()
    {
        type = Helper.getFromPattern("(^\\S*)\\s", query);

        if(!type.equals("show"))
        {
            schemas = Helper.getFromPattern("from\\s(\\S*)\\.", query).split(",");
            tables = Helper.getFromPattern("\\.(\\S*)\\s", query).split(",");
            columns = Helper.getFromPattern("select\\s(.*?)\\sfrom", query).split(",");
            String reg = "\\s(\\S*)='(\\S*)'";
            Pattern pat = Pattern.compile(reg);
            Matcher keymatcher = pat.matcher(query);

            List<String> fs = new ArrayList<>();
            List<String> vs = new ArrayList<>();
            while(keymatcher.find())
            {
                fs.add(keymatcher.group(1));
            }
            fields = new String[fs.size()];
            fields = fs.toArray(new String[0]);

            Matcher valmatcher = pat.matcher(query);
            while(valmatcher.find())
            {
                vs.add(valmatcher.group(2));
            }
            values = new String[vs.size()];
            values = vs.toArray(new String[0]);

            orders = Helper.getFromPattern("order by\\s(\\S*)$", query).split(",");
        }else
        {
            String re = "show\\s(\\S*)\\sfrom\\s(\\S*)";

            Pattern showpat = Pattern.compile(re);
            Matcher tabmatcher = showpat.matcher(query);

            while(tabmatcher.find())
            {
                schemas = tabmatcher.group(2).split(",");
            }


        }

    }



}
