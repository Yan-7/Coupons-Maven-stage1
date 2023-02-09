package db;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

//    public static final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
//    public static final String user = "root";
//    public static final String password = "1234";
//    private Connection connection =DriverManager.getConnection(url,user,password);



    public static final String CREATE_SCHEMA = "create schema couponsystem";
    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `couponsystem`.`categories` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT ,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));";
    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponsystem`.`companies` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NULL,\n" +
            "  `EMAIL` VARCHAR(45) NULL,\n" +
            "  `PASSWORD` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`ID`));";
    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `couponsystem`.`customers` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `FIRST_NAME` VARCHAR(45) NULL,\n" +
            "  `LAST_NAME` VARCHAR(45) NULL,\n" +
            "  `EMAIL` VARCHAR(45) NULL,\n" +
            "  `PASSWORD` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE `couponsystem`.`coupons` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `COMPANY_ID` INT NULL,\n" +
            "  `CATEGORY_ID` INT NULL,\n" +
            "  `TITLE` VARCHAR(45) NULL,\n" +
            "  `DESCRIPTION` VARCHAR(45) NULL,\n" +
            "  `START_DATE` DATETIME NULL,\n" +
            "  `END_DATE` DATETIME NULL,\n" +
            "  `AMOUNT` INT NULL,\n" +
            "  `PRICE` DOUBLE NULL,\n" +
            "  `IMAGE` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`ID`),\n" +
            "  INDEX `ID_COMPANY_FK_idx` (`COMPANY_ID` ASC) VISIBLE,\n" +
            "  INDEX `IC_CATEGORY_FK_idx` (`CATEGORY_ID` ASC) VISIBLE,\n" +
//            "  CONSTRAINT `ID_COMPANY_FK`\n" +
            "    FOREIGN KEY (`COMPANY_ID`)\n" +
            "    REFERENCES `couponsystem`.`companies` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
//            "  CONSTRAINT `ID_CATEGORY_FK`\n" +
            "    FOREIGN KEY (`CATEGORY_ID`)\n" +
            "    REFERENCES `couponsystem`.`categories` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    // TODO: 01/10/2022 coupons foreign key
    public static final String CREATE_TABLE_CUSTOMER_VS_COUPON = "CREATE TABLE `couponsystem`.`customers_vs_coupons` (\n" +
            "`CUSTOMER_ID` INT NOT NULL, \n" +
            "`COUPON_ID` INT NOT NULL,\n" +
    "PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\n" +
    "INDEX `COUPON_ID_FK_idx` (`COUPON_ID` ASC) VISIBLE,\n" +
    "CONSTRAINT `CUSTOMER_ID_FK`\n" +
    "FOREIGN KEY (`CUSTOMER_ID`)\n" +
    "REFERENCES `couponsystem`.`customers` (`ID`)\n" +
    "ON DELETE NO ACTION\n" +
    "ON UPDATE NO ACTION,\n" +
    "CONSTRAINT `COUPON_ID_FK`\n" +
    "FOREIGN KEY (`COUPON_ID`)\n" +
    "REFERENCES `couponsystem`.`coupons` (`ID`)\n" +
    "ON DELETE NO ACTION\n" +
    "ON UPDATE NO ACTION);";




    public DatabaseManager() throws SQLException {
    }

    public void createSchema() throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_SCHEMA);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Schema created");

    }
    public void createTableCategories() throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_CATEGORIES);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Table categories created");
    }

    public void createTable_companies() throws SQLException{

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_COMPANIES);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Table companies created");
    }

    public void createTableCustomers() throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_CUSTOMERS);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Table Customers created");
    }
    public void createTable_coupons() throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_COUPONS);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Table coupons created");
    }
    public void createTable_customerVsCoupons() throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_CUSTOMER_VS_COUPON);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
        System.out.println("Table customer VS coupon created");
    }

}
