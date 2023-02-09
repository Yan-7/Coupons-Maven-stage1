package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CouponsDBDAO;
import dao.CustomersDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {

    private int customerID;
    CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();


    // TODO: 17/10/2022 wtf the Ctor does?
    public CustomerFacade(int customerID) {
        this.customerID = customerID;
    }


    public boolean login(String email, String password) throws SQLException, InterruptedException {
        Customer customer = customersDBDAO.getOneCustomer(customerID);
        if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
            System.out.println("validation confirmed");
            return true;
        }
        System.out.println("company login error");
        return false;
    }

    //cannot purchase coupon more than once.
    //cannot purchase if quantity is 0.
    // cannot purchase if expired.
    // after purchase reduce amount by 1.

    public void purchaseCoupon(Coupon coupon) {
        customersDBDAO.addCouponToCustomer(customerID, coupon);
        couponsDBDAO.addCouponPurchase(customerID, coupon.getId());
    }

    public ArrayList<Coupon> getCustomerCoupons() {
        Customer customer = customersDBDAO.getOneCustomer(customerID);
        ArrayList<Coupon> coupons = customer.getCoupons();
        return coupons;
    }

    public ArrayList<Coupon> getCustomerCouponsByCategory(Category category) {
        Customer customer = customersDBDAO.getOneCustomer(customerID);
        ArrayList<Coupon> coupons = customer.getCoupons();
        ArrayList<Coupon> couponsByCategory = new ArrayList<>();
        for (Coupon c : coupons) {
            if (c.getCategory().equals(category)) {
                couponsByCategory.add(c);
            }
        }
        return couponsByCategory;
    }

    public ArrayList<Coupon> getCustomerCouponsByPrice(double maxPrice) {
        Customer customer = customersDBDAO.getOneCustomer(customerID);
        ArrayList<Coupon> coupons = customer.getCoupons();
        ArrayList<Coupon> couponsByPrice = new ArrayList<>();
        for (Coupon c : coupons) {
            if (c.getPrice() <= maxPrice) {
                couponsByPrice.add(c);
            }
        }
        return couponsByPrice;
    }

    public Customer getCustomerDetails() {
        Customer customer = customersDBDAO.getOneCustomer(customerID);
        return customer;
    }

//    ----------
}

