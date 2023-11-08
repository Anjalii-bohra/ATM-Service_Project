package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Model.Customer;
import Util.DatabaseConnectionManager;
import org.apache.commons.dbcp2.BasicDataSource;

public class CustomerDao {
    static BasicDataSource dataSource = DatabaseConnectionManager.getDataSource();
    static Connection connection;
    static Scanner scanner;

    AccountDao accountDao = new AccountDao();

    BankDao bankDao = new BankDao();
    public CustomerDao() {
        // Create a connection using the DatabaseConnectionManager
        try {
            connection = dataSource.getConnection();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return; // Return an error
        }

        try {
            connection.setAutoCommit(false); // Start a transaction

            System.out.print("Enter the Bank Id according to the ");
            bankDao.listBanks();

            System.out.print("Enter the Bank Id: ");
            int bankId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter the last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Validate the email format
//            if (!isValidEmail(email)) {
//                System.out.println("Invalid email address.");
//                connection.rollback(); // Rollback the transaction
//                return;
//            }

            System.out.print("Enter the phone number: ");
            String phoneNumber = scanner.nextLine();

            // Validate the phone number format
//            if (!isValidPhoneNumber(phoneNumber)) {
//                System.out.println("Invalid phone number format.");
//                connection.rollback(); // Rollback the transaction
//                return;
//            }

            System.out.print("Enter the address: ");
            String address = scanner.nextLine();

            System.out.print("Enter the Date Of Birth (YYYY-MM-DD): ");
            String DOB = scanner.nextLine();

            // Validate the date format
//            if (!isValidDate(DOB)) {
//                System.out.println("Invalid date format.");
//                connection.rollback(); // Rollback the transaction
//                return;
//            }

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Insert customer information into the database
            String insertSql = "INSERT INTO Customer (bank_id, first_name, last_name, email, phone_number, address, date_of_birth, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, bankId);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, email);
                ps.setString(5, phoneNumber);
                ps.setString(6, address);
                ps.setDate(7, java.sql.Date.valueOf(DOB));
                ps.setString(8, password);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int newCustomerId = generatedKeys.getInt(1);
                        System.out.println("New Customer Created");
                        if (accountDao.createAccount(newCustomerId)) {
                            System.out.println("Your new Customer Id is " + newCustomerId);
                            connection.commit(); // Commit the transaction
                        } else {
                            System.out.println("Failed to create an account for the customer.");
                            connection.rollback(); // Rollback the transaction
                        }
                    } else {
                        System.out.println("Failed to retrieve the new Customer ID");
                        connection.rollback(); // Rollback the transaction
                    }
                } else {
                    System.out.println("Failed to create a new customer.");
                    connection.rollback(); // Rollback the transaction
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(); // Rollback the transaction on exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCustomer() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return;
        }
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(customerId, password)) {
            System.out.println("Logged In ");

            System.out.print("Enter the new first name: ");
            String newFirstName = scanner.nextLine();

            System.out.print("Enter the new last name: ");
            String newLastName = scanner.nextLine();

            System.out.print("Enter new mail id: ");
            String newEmail = scanner.nextLine();

            System.out.print("Enter new phone number: ");
            String newPhone_number = scanner.nextLine();

            System.out.print("Enter new address: ");
            String newAddress = scanner.nextLine();

            System.out.print("Enter new Date Of Birth: ");
            String newDOB = scanner.nextLine();

            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();

            String updateSql = "UPDATE Customer SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, date_of_birth = ?, password = ? WHERE customer_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setString(1, newFirstName);
                ps.setString(2, newLastName);
                ps.setString(3, newEmail);
                ps.setString(4, newPhone_number);
                ps.setString(5, newAddress);
                ps.setDate(6, java.sql.Date.valueOf(newDOB));
                ps.setString(7, newPassword);
                ps.setInt(8, customerId);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Customer details are updated");
                } else {
                    System.out.println("Failed to update customer details.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Check the Password or Id again");
        }
    }

    public void deleteCustomer() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
        }
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(customerId, password)) {
            System.out.println("Logged In ");

            String deleteSql = "DELETE FROM Customer WHERE customer_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
                ps.setInt(1, customerId);
                accountDao.deleteAccount();
                int rows = ps.executeUpdate();
                System.out.println("Customer Deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Check the Password or Id again");
        }
    }

    public static List<Customer> listCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String selectSql = "SELECT * FROM Customer";
        try (PreparedStatement ps = connection.prepareStatement(selectSql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                int bankId = resultSet.getInt("banK_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                Date createdAt = resultSet.getDate("created_at");
                Customer customer = new Customer(bankId, customerId, firstName, lastName, email, phoneNumber, address, dateOfBirth, createdAt);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!customerList.isEmpty()) {
            System.out.println("List of Customers:");
            for (Customer customer : customerList) {
                System.out.println("Customer ID: " + customer.getCustomerId() + ", Name: " + customer.getFirstName() +
                        " " + customer.getLastName() + ", Email: " + customer.getEmail() + ", Phone Number: "
                        + customer.getPhoneNumber() + ", Address: " + customer.getAddress() + ", Date of Birth: " + customer.getDateOfBirth());
            }
        } else {
            System.out.println("No customers found.");
        }
        return customerList;
    }

    private  boolean isValidPassword(int customerId, String password) {
        String storedPassword = getPasswordById(customerId);
        return storedPassword != null && storedPassword.equals(password);
    }
    public String getPasswordById(int id) {
        String query = "SELECT password FROM Customer where customer_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                } else {
                    return "Not valid Id";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
