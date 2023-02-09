package dao;


// the DAO classes allows us to do CRUD (create, read, update, delete). it doesn't do the logic of the program, but general CRUD,
// The class gets java beans objects or primitives and turn them into SQL qua quarries and execute them in the database.
// they can also return bean objects, collections.
// the DAO classes use the connection pool  to get connection to the DB.

import beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {

    boolean isCompanyExists(String email,String password) throws SQLException, InterruptedException;
    public void addCompany (Company company) throws SQLException, InterruptedException;
    void updateCompany (Company company) throws SQLException, InterruptedException;
    void deleteCompany (int companyID) throws SQLException, InterruptedException;
    ArrayList<Company> getAllCompanies () throws SQLException, InterruptedException;
    Company getOneCompany(int companyID) throws SQLException, InterruptedException;

}
