package db;

import java.sql.Connection;

public interface ConnectionPoolInterface {

    Connection getConnection();
     void restoreConnection(Connection connection);




}
