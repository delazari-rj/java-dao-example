package model.dao;

import util.PropertiesManager;

import java.sql.*;
import java.util.Properties;

public class DBConnection {



    public static Connection getConnection(){

        Connection conn = null;

        if(conn == null){
            try {
                Properties props = PropertiesManager.loadProperties("application.properties");
                String url = props.getProperty("database.url");
                conn = DriverManager.getConnection(url, props);
            }catch(SQLException e){
                throw new DBException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnetion(Connection conn){
        if(conn != null){
            try {
                conn.close();
            }catch(SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void closeStatemant(Statement st){
        try {
            if (st != null) {
                st.close();
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }
}