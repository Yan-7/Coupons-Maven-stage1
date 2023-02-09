import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CompaniesDBDAO;
import dao.CouponsDBDAO;
import dao.CustomersDBDAO;
import db.ConnectionPool;
import db.DatabaseManager;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws SQLException, InterruptedException {


        //for testing

        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        DatabaseManager databaseManager = new DatabaseManager();

        Coupon coupon1 = new Coupon(5000, 1233, Category.valueOf("Electricity"), "screen", "good screen", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 5, 500, "imgage");
        Coupon coupon2 = new Coupon(5001, 1233, Category.valueOf("Electricity"), "screen2", "good screen2", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 2, 5002, "imgage2");

        ArrayList<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon1);
        Company company1 = new Company(2, "office", "office@", "123", coupons);
        Company company2 = new Company(1, "office2", "office@2", "567", coupons);
        Customer customer1 = new Customer(1, "a", "aa", "a@", "123");
        Customer customer2 = new Customer(2, "b", "bb", "b@", "123");

//            databaseManager.createSchema();
//            databaseManager.createTableCategories();
//            databaseManager.createTableCustomers();
//            databaseManager.createTable_companies();
//            databaseManager.createTable_coupons();
//            databaseManager.createTable_customerVsCoupons();

//            companiesDBDAO.addCompany(company1);
//            companiesDBDAO.updateCompany(company2);
//                companiesDBDAO.deleteCompany(2);
//            companiesDBDAO.isCompanyExists("offkice","123");
//            companiesDBDAO.getAllCompanies();
//            companiesDBDAO.getOneCompany(1);

//        customersDBDAO.isCustomerExists("a3a","123");
//        customersDBDAO.addCustomer(customer1);
//        customersDBDAO.addCustomer(customer2);
//        customersDBDAO.updateCustomer(customer2);
//        customersDBDAO.deleteCustomer(1);
//        customersDBDAO.getAllCustomers();

//        couponsDBDAO.addCoupon(coupon1);
//        couponsDBDAO.updateCoupon(coupon2);
//        couponsDBDAO.deleteCoupon(2);
//        couponsDBDAO.getAllCoupons();
//        couponsDBDAO.addCouponPurchase(100,1);
//couponsDBDAO.deleteCouponPurchase(100,1);
        AdminFacade adminFacade = new AdminFacade();
        adminFacade.addCompany(company1);
//        CompanyFacade companyFacade = new CompanyFacade(5000);
////        companyFacade.addNewCoupon(coupon2);
//        companyFacade.updateCoupon(coupon1);


    }
}
