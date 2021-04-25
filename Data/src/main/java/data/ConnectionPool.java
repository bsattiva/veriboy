package data;

import common.Helper;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bsati
 */
public class ConnectionPool implements Serializable {

    transient BasicDataSource source;

    public String database;
    final String name;
    final String password;

    final DataSource dataSource;

    public ConnectionPool(String db, String nm, String ps)
    {
        database = db;
        name = nm;
        password = ps;

        dataSource = getDataSource();
    }



    public Connection getConnection()
    {



        Connection result = null;


        try {


            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return result;
    }

    final DataSource getDataSource()
    {

        DataSource ds = null;

        try {
            ds = new DataSource();
            //         ds.setDriverClassName("com.mysql.jdbc.Driver");
            String hostname = Helper.getStringFromProperties(Helper.getHomeDir(new String[]{"config.properties"}),"db","127.0.0.1");
            ds.setUrl("jdbc:mysql://" + hostname + ":3306/" + database + "?useUnicode=yes&characterEncoding=UTF-8");
            ds.setUsername(name);
            ds.setPassword(password);
            ds.setMaxIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            source = ds;
        } catch (Throwable e) {
            e.printStackTrace();
        }



        return (DataSource)source;

    }



}