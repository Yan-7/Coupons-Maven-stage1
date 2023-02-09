package dao;

import beans.Company;
import beans.Coupon;
import db.ConnectionPool;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {


    public CompaniesDBDAO() throws SQLException {
    }

    // TODO: 03/10/2022  - does not work, return alwats true
    @Override
    public boolean isCompanyExists(String email, String pass) throws SQLException, InterruptedException {
        final String QUERY_IS_COMPANY_EXIST = "select exists( SELECT * FROM `couponsystem`.companies where email=? and password=?) as res";
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(QUERY_IS_COMPANY_EXIST);
            statement.setString(1, email);
            statement.setString(2, pass);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()){
                int amount = resultSet.getInt(1);
                if (amount>0) {
                    System.out.println("company id " + resultSet.getString(1) + " exists");
                    ConnectionPool.getInstance().restoreConnection(conn);
                    return true;

                } else {
                    System.out.println("company  is not found");
                }
                ConnectionPool.getInstance().restoreConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addCompany(Company company) throws SQLException, InterruptedException {

        String ADD_COMPANY = "INSERT INTO `couponsystem`.`companies` (`ID`, `NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(ADD_COMPANY, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, company.getId());
            pstmt.setString(2, company.getName());
            pstmt.setString(3, company.getName());
            pstmt.setString(4, company.getPassword());
            pstmt.execute();
            System.out.println("company " + company.getName() + " added");
            ConnectionPool.getInstance().restoreConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCompany(Company company) throws SQLException, InterruptedException {
        //not allowed to change id or name
//        final String UPDATE_COMPANY = "UPDATE `couponsystem`.`companies` SET `ID` = ?, `NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?)";
        final String UPDATE_COMPANY = "UPDATE `couponsystem`.`companies`  `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?)";
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement ptsmt = conn.prepareStatement(UPDATE_COMPANY, PreparedStatement.RETURN_GENERATED_KEYS);
//            ptsmt.setInt(1, company.getId());
//            ptsmt.setString(2, company.getName());
            ptsmt.setString(1, company.getEmail());
            ptsmt.setString(2, company.getPassword());
            ptsmt.setInt(3, company.getId());
            ptsmt.execute();
            System.out.println("company " + company.getName() + " updated");
            ConnectionPool.getInstance().restoreConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCompany(int companyID) throws SQLException, InterruptedException {
// TODO: 17/10/2022  delete coupons history
        //delete the coupons
        Company company = getOneCompany(companyID);
        ArrayList<Coupon> nullCoupons = new ArrayList<>();
        company.setCoupons(nullCoupons);
        final String DELETE_COMPANY = "DELETE FROM `couponsystem`.`companies` WHERE (`ID` = ?)";

        //delete the company in the sql db
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(DELETE_COMPANY);
            statement.setInt(1, companyID);
            statement.execute();
            System.out.println("company deleted");
            ConnectionPool.getInstance().restoreConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException {
        final String GET_ALL_COMPANIES = "SELECT * FROM couponsystem.companies";
        ArrayList<Company> allCompanies = new ArrayList<>();
            try {
                Connection conn = ConnectionPool.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(GET_ALL_COMPANIES);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Company currentCompany = new Company();
                    currentCompany.setId(resultSet.getInt(1));
                    currentCompany.setName(resultSet.getString(2));
                    currentCompany.setEmail(resultSet.getString(3));
                    currentCompany.setPassword(resultSet.getString(4));
                    System.out.println(currentCompany);
                    allCompanies.add(currentCompany);
                    ConnectionPool.getInstance().restoreConnection(conn);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        return allCompanies;
    }

    @Override
    public Company getOneCompany(int companyID) throws SQLException, InterruptedException {
         final String GET_ONE_COMPANY = "SELECT * FROM couponsystem.companies where id = ?";
         try {
             Company company = new Company();
             Connection conn = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ONE_COMPANY);
             statement.setInt(1,companyID);
             statement.execute();
             ResultSet resultSet = statement.getResultSet();
             while (resultSet.next()) {
                 company.setId(companyID);
                 company.setName(resultSet.getString(2));
                 company.setEmail(resultSet.getString(3));
                 company.setPassword(resultSet.getString(4));
                 System.out.println(company);
                 ConnectionPool.getInstance().restoreConnection(conn);
                 return company;
             }
//             currentCompany.setId(resultSet.getInt(1));
         } catch (SQLException e) {
             e.printStackTrace();
         }
        System.out.println("no such company");
         return null;

    }
}
