package Service;

import DAO.CustomerDao;

import java.sql.SQLException;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService() {
        customerDao = new CustomerDao();
    }

    public void createCustomer() throws SQLException {
        customerDao.createCustomer();
    }

    public void updateCustomer() {
        customerDao.updateCustomer();
    }

    public void deleteCustomer() {
        customerDao.deleteCustomer();
    }

    public void listCustomers() {
        CustomerDao.listCustomers();
    }

}