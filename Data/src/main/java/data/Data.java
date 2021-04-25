package data;


import common.Helper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data {
    String user;
    String password;
    private List<ConnectionPool> pool;

    public Data(String user, String password){
        this.user = user;
        this.password = password;
    }

    ConnectionPool getPool(String query)
    {
        ConnectionPool result = null;
        ConnectionPool connectionPool = null;
        Query q = new Query(query);
        String schema = q.schemas[0];
        if(pool == null)
        {
            pool = new ArrayList<>();
            connectionPool = new ConnectionPool(schema,user,password);
            pool.add(connectionPool);

        }else
        {
            for(ConnectionPool p:pool)
            {
                if(p.database.equals(schema))
                {
                    connectionPool = p;
                    break;
                }
            }

            if(connectionPool == null)
            {
                connectionPool = new ConnectionPool(schema,user,password);
                pool.add(connectionPool);
            }
        }
        result = connectionPool;
        return result;
    }

    public Object pullObject(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object result = null;
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(q);

            rs = ps.executeQuery();

            while(rs.next())
            {
                if(result == null)
                {
                    result = rs.getObject(1);
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(connection != null)
                {
                    connection.close();

                }
                if(ps!=null)
                    ps.close();
                if(rs!=null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                if(connection!=null){

    }


        return result;
    }

    public String Execute(final String q)
    {
        String result = "Unknown failure";

        Query query = new Query(q);
        String schema = query.schemas[0];

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ConnectionPool connectionPool = getPool(q);

            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(q.replace("&amp;&amp;dot&amp;&amp;","."));

            ps.executeUpdate();
            result = "success";
        }catch(Exception x)
        {
            result = "Error: " + x.getMessage();
        }finally
        {
            try {
                if(connection!=null)
                    connection.close();
                //               connection = null;
                if(ps!=null)
                {
                    ps.close();
                }
                if(rs!=null)
                    rs.close();
            } catch (SQLException sQLException) {
            }



        }
        return result;
    }


    public JSONArray pullTable(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray array = new JSONArray();

        String[] columns = Helper.getFromPattern("^select (.*?) from",q).split(",");
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(q);

            rs = ps.executeQuery();

            while(rs.next())
            {
                JSONObject result = new JSONObject("{}");
                for(String column:columns){
                    String r = rs.getString(column);
                    if(Helper.isThing(r)){
                        result.put(column,r);

                    }
                }

                array.put(result);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(connection != null)
                {
                    connection.close();
                    //           connection = null;
                }
                if(ps!=null)
                    ps.close();
                if(rs!=null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return array;
    }


    public JSONObject pullArray(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONObject result = new JSONObject("{}");

        String[] columns = Helper.getFromPattern("^select (.*?) from",q).split(",");
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(q);

            rs = ps.executeQuery();

            while(rs.next())
            {

                for(String column:columns){
                    String r = rs.getString(column);
                    if(Helper.isThing(r)){
                        result.put(column,r);

                    }
                }

//                if(result == null)
//                {
//                    result = rs.getString(1);
//                    break;
//                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(connection != null)
                {
                    connection.close();
                    //           connection = null;
                }
                if(ps!=null)
                    ps.close();
                if(rs!=null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }



    public String pullString(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = null;
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(q);

            rs = ps.executeQuery();

            while(rs.next())
            {
                if(result == null)
                {
                    result = rs.getString(1);
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(connection != null)
                {
                    connection.close();
                    //           connection = null;
                }
                if(ps!=null)
                    ps.close();
                if(rs!=null)
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }

    public List<String> pullList(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Query qr = new Query(q);
        //     qr.set(new Feed());

        List<String> result = new ArrayList<>();
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            try {
                ps = connection.prepareStatement(q);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            rs = ps.executeQuery();


            List<String> l = new ArrayList<>();

            while(rs.next())
            {
                result.add(rs.getString(qr.columns[0].replace("distinct ",""))
                        //       .replace("'", "")
                );

            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(!connection.isClosed())
                {
                    connection.close();
                    //            connection = null;


                }
                if(ps!=null){
                    ps.close();
                }

                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(connection!=null)
            System.out.print("counght ya");
        return result;
    }

    public Map<String,String> pullMap(String q)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Query qr = new Query(q);
        //     qr.set(new Feed());

        Map<String,String> result = new HashMap<>();
        try {

            ConnectionPool connectionPool = getPool(q);
            connection = connectionPool.getConnection();

            try {
                ps = connection.prepareStatement(q);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            rs = ps.executeQuery();


            List<String> l = new ArrayList<>();

            while(rs.next())
            {
                String key = rs.getString(qr.columns[0]);
                String val = rs.getString(qr.columns[1]);
                result.put(key,val);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            try {
                if(!connection.isClosed())
                {
                    connection.close();
                    //            connection = null;


                }
                if(ps!=null){
                    ps.close();
                }

                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(connection!=null)
            System.out.print("counght ya");
        return result;
    }
}
