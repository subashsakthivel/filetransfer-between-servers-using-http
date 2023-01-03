package com.example.testserver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/"+dbname;
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", pass);
            //props.setProperty("ssl", "true");
            conn = DriverManager.getConnection(url, props);
            if(conn!=null) System.out.println("Connection Established");
            else System.out.println("Connection Failed");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  conn;
    }

    public void createTable(Connection conn, String table_name) {
        Statement statement;
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + table_name + "(id SERIAL PRIMARY KEY, name varchar(100) NOT NULL, location varchar(200) NOT NULL, size varchar(200), creation_time varchar(200) NOT NULL);";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void insert_row(Connection conn, String table_name,String filename, String fileLocation, String creation_time, String size) {
        Statement statement;
        try {
            String query = String.format("insert into %s(name,location,size,creation_time) values('%s','%s','%s','%s');",table_name,filename,fileLocation,size,creation_time);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(filename + " inserted");
        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public ResultSet fetch_data(Connection conn, String table_name) {
        Statement statement;
        ResultSet rs= null;
        try {
            String query = String.format("select * from %s",table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return rs;
    }
}
