package facade;

import dao.CompaniesDAO;
import dao.CouponsDAO;
import dao.CustomersDAO;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;

    protected CustomersDAO customersDAO;

    protected CouponsDAO couponsDAO;

    public boolean login(String email, String password) throws SQLException, InterruptedException {
        return true;
    }

}
