package dao;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {

    @Override
    // TODO: 09/10/2022 not working properly - problem with category id
    public void addCoupon(Coupon coupon) {
        final String ADD_COUPON = "INSERT INTO `couponsystem`.`coupons` (`ID`, `COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?,?)";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, coupon.getId());
            statement.setInt(2, coupon.getCompanyId());
// TODO: 14/10/2022
            Category category1 = coupon.getCategory();
            int categoryIndex = category1.ordinal();
            statement.setInt(3, categoryIndex);
            statement.setString(4, coupon.getTitle());
            statement.setString(5, coupon.getDescription());
            statement.setDate(6, (Date) coupon.getStartDate());
            statement.setDate(7, (Date) coupon.getEndDate());
            statement.setInt(8, coupon.getAmount());
            statement.setInt(9, (int) coupon.getPrice());
            statement.setString(10, coupon.getImage());
            statement.executeUpdate();

            System.out.println("Coupon " + coupon.getId() + " added");
            ConnectionPool.getInstance().restoreConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) {
        // cannot update coupon code && company id
//        final String UPDATE_COUPON = "UPDATE `couponsystem`.`coupons` SET `ID` = ?, `COMPANY_ID` = ?, `CATEGORY_ID` = ?, `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ? WHERE (`ID` = ?)";
        final String UPDATE_COUPON = "UPDATE `couponsystem`.`coupons` SET , `CATEGORY_ID` = ?, `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ? WHERE (`ID` = ?)";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON,PreparedStatement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, coupon.getId());
//            statement.setInt(2, coupon.getCompanyId());
            statement.setInt(1, coupon.getCategory().ordinal());
            statement.setString(2, coupon.getTitle());
            statement.setString(3, coupon.getDescription());
            statement.setDate(4, (Date) coupon.getStartDate());
            statement.setDate(5, (Date) coupon.getEndDate());
            statement.setInt(6, coupon.getAmount());
            statement.setInt(7, (int) coupon.getPrice());
            statement.setInt(8, coupon.getId());
            statement.execute();
            ConnectionPool.getInstance().restoreConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Coupon updated");

    }

    @Override
    // TODO: 17/10/2022 delete the purchase history of customers 
    public void deleteCoupon(int couponID) {

        final String DELETE_COUPON = "DELETE FROM `couponsystem`.`coupons` WHERE (`ID` = ?)";
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON);
            statement.setInt(1, couponID);
            statement.execute();
            ConnectionPool.getInstance().restoreConnection(connection);
            System.out.println("coupon " + couponID + " deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        final String GET_ALL_COUPONS = "SELECT * FROM couponsystem.coupons";

        ArrayList<Coupon> list = null;
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            list = new ArrayList<>();
            while (resultSet.next()) {

                Coupon currentCoupon = new Coupon();

                currentCoupon.setId(resultSet.getInt(1));
                currentCoupon.setCompanyId(resultSet.getInt(2));
                int couponIndex = resultSet.getInt(3);
                currentCoupon.setCategory(Category.values()[couponIndex]);
                currentCoupon.setTitle(resultSet.getString(4));
                currentCoupon.setDescription(resultSet.getString(5));
                currentCoupon.setStartDate(resultSet.getDate(6));
                currentCoupon.setStartDate(resultSet.getDate(7));
                currentCoupon.setAmount(resultSet.getInt(8));
                currentCoupon.setPrice(resultSet.getInt(9));

                list.add(currentCoupon);
            }
            System.out.println(list); // TODO: 09/10/2022 print a nice list
            ConnectionPool.getInstance().restoreConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        final String GET_ONE_COUPON = "SELECT * FROM couponsystem.coupons where id = ?";
        try {
            Coupon currentCoupon = new Coupon();
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON);
            statement.setInt(1, couponID);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                currentCoupon.setId(resultSet.getInt(1));
                currentCoupon.setCompanyId(resultSet.getInt(2));
//                currentCoupon.setCategory(resultSet.));
                currentCoupon.setTitle(resultSet.getString(4));
                currentCoupon.setDescription(resultSet.getString(5));
                currentCoupon.setStartDate(resultSet.getDate(6));
                currentCoupon.setStartDate(resultSet.getDate(7));
                currentCoupon.setAmount(resultSet.getInt(8));
                currentCoupon.setPrice(resultSet.getInt(9));

                return currentCoupon;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        System.out.println("coupon not found");
        return null;
    }

    @Override // TODO: 14/10/2022 array list not working properly
    public void addCouponPurchase(int customerID, int couponID) {
        final String UPDATE_COUPON_PURCHASE = "UPDATE `couponsystem`.`coupons` SET `AMOUNT` = ? WHERE (`ID` = ?)";
        final String FIND_COUPON_BY_ID = "SELECT * FROM couponsystem.coupons WHERE (`ID` = ?)";

        try {
            //FIND COUPON
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_COUPON_BY_ID);
            PreparedStatement statement2 = connection.prepareStatement(UPDATE_COUPON_PURCHASE);
            statement.setInt(1, couponID);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int couponsAmount = resultSet.getInt(8);
                couponsAmount--;
                System.out.println("amount of coupons available: " + couponsAmount);
                statement2.setInt(1, couponsAmount);
                statement2.setInt(2, couponID);
                statement2.execute();

                //updates the customer
                CustomersDBDAO customersDBDAO = new CustomersDBDAO();
                Customer customer1 = customersDBDAO.getOneCustomer(customerID);
                Coupon coupon1 = getOneCoupon(couponID);

                ArrayList<Coupon> list = new ArrayList<>();
                list.add(coupon1);
                customer1.setCoupons(list);
                list.addAll(customer1.getCoupons());
                ConnectionPool.getInstance().restoreConnection(connection);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    // TODO: 12/10/2022 fix method 
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        final String UPDATE_COUPON_PURCHASE = "UPDATE `couponsystem`.`coupons` SET `AMOUNT` = ? WHERE (`ID` = ?)";
        final String FIND_COUPON_BY_ID = "SELECT * FROM couponsystem.coupons WHERE (`ID` = ?)";

        try {
            //FIND COUPON
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_COUPON_BY_ID);
            PreparedStatement statement2 = connection.prepareStatement(UPDATE_COUPON_PURCHASE);

            statement.setInt(1, couponID);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int couponsAmount = resultSet.getInt(8);
                couponsAmount++;
                System.out.println("amount of coupons available: " + couponsAmount);
                statement2.setInt(1, couponsAmount);
                statement2.setInt(2, couponID);
                statement2.execute(); //updates the coupon
            }
            //update customer coupons list:
            CustomersDBDAO customersDBDAO = new CustomersDBDAO();
            Customer customer1 = customersDBDAO.getOneCustomer(customerID);
            Coupon coupon1 = getOneCoupon(couponID);
            ArrayList<Coupon> list = customer1.getCoupons();
            if (list.size() > 0) {
                list.remove(coupon1);
                System.out.println("coupon removed");  // TODO: 14/10/2022 not working 
            } else {
                System.out.println("coupon not found");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
