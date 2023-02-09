package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

// the connection pool allows us to manage a number of connection to the database. those connections are
// saved in a stack type  collection.

public class ConnectionPool implements ConnectionPoolInterface {

    //connection instance
    private static final String URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String PASSWORD = "1234";
    private static final String USER = "root";

    private Set<Connection> connectionPool;
    private static final int poolSize = 5;
    //instance
    private static ConnectionPool instance;

    // constructor
    private  ConnectionPool() throws SQLException {
        this.connectionPool = createConnections();
    }

    //getter
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }


    private static Set<Connection> createConnections() throws SQLException {
        Set<Connection> pool = new HashSet<>();
        for (int i = 0; i < poolSize; i++) {
            Connection connection =DriverManager.getConnection(URL, USER, PASSWORD);
            pool.add(connection);
        }
        return pool;
    }

    public Set<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public Connection getConnection() {
        Connection con = connectionPool.iterator().next();
        connectionPool.remove(con);
        return con;
    }

    @Override
    public void restoreConnection(Connection connection) {
        connectionPool.add(connection);
    }


    public void closeAllConnections() throws SQLException {
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}
