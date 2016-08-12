package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static Connection connection = null;
    private static DatabaseConnection instance = null;
    
    static String SqlDriver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/visit1?user=user&password=user";
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        if (!isConnected()) {
            // TODO: check success
            connect();

        }
        return connection;
    }
    
    public boolean isConnected() {
        try {
            return (connection != null) && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public synchronized boolean connect() {
        if (!isConnected()) {
            try {
                Class.forName(SqlDriver);
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                return false;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }
        
        return true;
    }
}
