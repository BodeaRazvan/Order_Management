package org.orderManagement.connection;
import java.sql.*;

/**
 * Class that handles connecting / disconnecting to/from the database
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "varsator1";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private Connection createConnection(){
        try{
            return DriverManager.getConnection(DBURL,USER,PASS);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    public static void close(Connection connection){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(Statement statement){
        try{
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet){
        try {
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
