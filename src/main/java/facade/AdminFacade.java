package facade;

import beans.Company;
import beans.Customer;
import dao.CompaniesDAO;
import dao.CompaniesDBDAO;
import dao.CustomersDAO;
import dao.CustomersDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    private final static String email = "admin@admin.com";
    private final static String password = "admin";

    CompaniesDAO companiesDBDAO = new CompaniesDBDAO();
    CustomersDAO customersDBDAO = new CustomersDBDAO();


    //Ctor
    public AdminFacade() throws SQLException {
    }

    // TODO: 14/10/2022
    public boolean login(int password, String email) {
        return true;
    }

    public void addCompany(Company company) throws SQLException, InterruptedException {
        String email = company.getEmail();
        String password = company.getPassword();
        if (companiesDBDAO.isCompanyExists(email, password) == true) {
            System.out.println("company already exists, cannot add");
            return;
        }
        companiesDBDAO.addCompany(company);
    }

    public void updateCompany(Company company) throws SQLException, InterruptedException {
        companiesDBDAO.updateCompany(company);
    }

    public void deleteCompany(int companyID) throws SQLException, InterruptedException {
        companiesDBDAO.deleteCompany(companyID);
    }

    public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException {
        ArrayList<Company> companies = companiesDBDAO.getAllCompanies();
        return companies;
    }

    public void getCompanyById(int companyID) throws SQLException, InterruptedException {
        companiesDBDAO.getOneCompany(companyID);
    }

    public void addClient(Customer customer) {
        customersDBDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        customersDBDAO.updateCustomer(customer);
    }

    public void deleteClient(int customerID) {
        customersDBDAO.deleteCustomer(customerID);
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = customersDBDAO.getAllCustomers();
        return customers;
    }

    public Customer getCustomerByID(int customerID) {
        customersDBDAO.getOneCustomer(customerID);
        return null;
    }
}
