package dao;

import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {

    // TODO: 09/10/2022  insert coupons arrayList
    @Override
    public boolean isCustomerExists(String email, String password) {
        final String QUERY_IS_CUSTOMER_EXISTS = "select exists( SELECT * FROM `couponsystem`.`customers` where email=? and password=?) as res";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt(1);
                if (amount > 0) {
                    System.out.println("customer id: " + resultSet.getString(1) + " exists");
                    ConnectionPool.getInstance().restoreConnection(connection);
                    return true;
                } else {
                    System.out.println("customer does not exist");
                    ConnectionPool.getInstance().restoreConnection(connection);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void addCustomer(Customer customer) {
        if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
            System.out.println("customer already exists, cannot add");
            return;

        }
        String ADD_COMPANY = "INSERT INTO `couponsystem`.`customers` (`ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?,?)";
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(ADD_COMPANY, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPassword());
            pstmt.execute();
            System.out.println("customer " + customer.getFirstName() + " added");
            ConnectionPool.getInstance().restoreConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        // cannot update customer id
//        final String UPDATE_Customer = "UPDATE `couponsystem`.`customers` SET `ID` = ?, `FIRST_NAME` = ?,`LAST_NAME`=? ,`EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?)";
        final String UPDATE_Customer = "UPDATE `couponsystem`.`customers` SET  `FIRST_NAME` = ?,`LAST_NAME`=? ,`EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?)";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_Customer, Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.execute();
            ConnectionPool.getInstance().restoreConnection(connection);
            System.out.println("customer updated");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        //delete coupons array:
        // TODO: 17/10/2022 does it works? 
        Customer customer = getOneCustomer(customerID);
        ArrayList<Coupon> couponsNull = new ArrayList<>();
        customer.setCoupons(couponsNull);

        final String DELETE_CUSTOMER = "DELETE FROM `couponsystem`.`customers` WHERE (`ID` = ?)";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customerID);
            statement.executeUpdate();
            ConnectionPool.getInstance().restoreConnection(connection);
            System.out.println("customer deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        final String GET_ALL_CUSTOMERS = "SELECT * FROM couponsystem.customers";
        ArrayList<Customer> arrayList1 = new ArrayList();
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(4));
                customer.setPassword(resultSet.getString(5));
                arrayList1.add(customer);
            }
            ConnectionPool.getInstance().restoreConnection(connection);
            System.out.println(arrayList1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList1;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        final String GET_ONE_Customer = "SELECT * FROM couponsystem.customers where id = ?";
        try {
            Customer customer = new Customer();
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_Customer);
            statement.setInt(1,customerID);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {

                customer.setId(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setEmail(resultSet.getString(4));
                customer.setPassword(resultSet.getString(5));
                // TODO: 17/10/2022  does it works? 
                customer.setCoupons(customer.getCoupons());
                return customer;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("customer not found");
        return null;
    }
//--------------------------
    public void addCouponToCustomer(int customerID, Coupon coupon) {
        Customer customer = getOneCustomer(customerID);
        ArrayList<Coupon> coupons = customer.getCoupons();
        coupons.add(coupon);

    }
}
