package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import dao.CompaniesDBDAO;
import dao.CouponsDAO;
import dao.CouponsDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

    private int companyID; //??
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
    CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();


    public CompanyFacade(int companyID) throws SQLException {
        this.companyID = companyID;
    }


    public boolean login(String email, String password) throws SQLException, InterruptedException {
        Company company = companiesDBDAO.getOneCompany(companyID);
        if (company.getEmail().equals(email) && company.getPassword().equals(password)) {
            System.out.println("validation confirmed");
            return true;
        }
        System.out.println("company login error");
        return false;
    }

    public void addNewCoupon(Coupon coupon) {

        try {
            Company company = companiesDBDAO.getOneCompany(companyID);
            ArrayList<Coupon> companyCoupons = company.getCoupons();
            for (Coupon c : companyCoupons) {
                if (c.getTitle().equals(coupon.getTitle())) {
                    System.out.println("same title is not allowed, action failed");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        couponsDBDAO.addCoupon(coupon);
    }


    public void updateCoupon(Coupon coupon) {
        couponsDBDAO.updateCoupon(coupon);

    }

    public void deleteCoupon(int couponID) {
        couponsDBDAO.deleteCoupon(couponID);
    }

    // TODO: 17/10/2022 make sure it works - important for other methods
    public ArrayList<Coupon> getAllCompanyCoupons() {
        ArrayList<Coupon> allCoupons = new ArrayList<>();
        ArrayList<Coupon> companyCoupons = new ArrayList<>();
        allCoupons = couponsDBDAO.getAllCoupons();
        for (Coupon c:allCoupons) {
            if (c.getCompanyId() == companyID) {
                companyCoupons.add(c);
            }
        }
        return companyCoupons;
    }

    public ArrayList<Coupon> getAllCouponsFromCategory(Category category) {
        ArrayList<Coupon> coupons1 = getAllCompanyCoupons();
        ArrayList<Coupon> coupons2 = new ArrayList<>();
        for (Coupon c : coupons1) {
            if (c.getCategory().equals(category)) {
                coupons2.add(c);
            }
        }
        return coupons2;
    }

    public ArrayList<Coupon> getAllCouponsByMaxPrice(double maxPrice) {
        ArrayList<Coupon> coupons1 = getAllCompanyCoupons();
        ArrayList<Coupon> coupons2 = new ArrayList<>();
        for (Coupon c : coupons1) {
            if (c.getPrice() <= maxPrice) {
                coupons2.add(c);
            }
        }
        return coupons2;
    }

    public Company getCompanyDetails() throws SQLException, InterruptedException {
        Company company = companiesDBDAO.getOneCompany(companyID);
        System.out.println(company);
        return company;
    }
}
