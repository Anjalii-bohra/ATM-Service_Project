package DAO;

import Model.AccountType;
import Util.DatabaseConnectionManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountDao {
    static BasicDataSource dataSource = DatabaseConnectionManager.getDataSource();
    static Connection connection;
    static Scanner scanner;

    public AccountDao() {
       try {
            connection = dataSource.getConnection();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(int customerId) {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false; // Return an error
        }

        // Generate a unique account number
        String accountNumber = generateAccountNumber();

        System.out.print("Enter Account Type:\n 1. Savings \n 2. Current: ");
        int accountTypeValue = Integer.parseInt(scanner.nextLine());

        // Validate the account type input
        AccountType accountType = AccountType.getByValue(accountTypeValue);
        if (accountType == null) {
            System.out.println("Invalid account type selection.");
            return false;
        }

        System.out.print("Enter the initial balance: ");
        double initialBalance = Double.parseDouble(scanner.nextLine());

        // Validate the initial balance input
        if (initialBalance < 0) {
            System.out.println("Initial balance must be a non-negative value.");
            return false;
        }

        System.out.print("Enter Account PIN (4-digit pin): ");
        int password = Integer.parseInt(scanner.nextLine());

        // Validate the password input
        if (password < 1000 || password > 9999) {
            System.out.println("Password must be a 4-digit number.");
            return false;
        }

        // Insert customer information into the database
        String insertSql = "INSERT INTO Accounts (customer_id, account_number, account_type, balance, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, customerId);
            ps.setString(2, accountNumber);
            ps.setString(3, accountType.getType());
            ps.setDouble(4, initialBalance);
            ps.setInt(5, password);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("New Account Created with Account Number: " + accountNumber);
                return true;
            } else {
                System.out.println("Failed to create a new account.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateAccountBalance() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Account PIN: ");
        String password = scanner.nextLine();

        if (isValidPassword(accountNumber, password)) {
            System.out.println("Logged In ");

            System.out.println("Enter the updated Balance ");
            double newBalance = Double.parseDouble(String.valueOf(scanner.nextDouble()));

            String updateSql = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setDouble(1, newBalance);
                ps.setString(2, accountNumber);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Account balance updated successfully.");
                } else {
                    System.out.println("Failed to update account balance. Account not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAccountType() {
        System.out.println("Under Maintenance");
//        System.out.print("Enter Account Number: ");
//        String accountNumber = scanner.nextLine();
//
//        System.out.print("Enter Account PIN: ");
//        String password = scanner.nextLine();
//
//        if (isValidPassword(accountNumber, password)) {
//
//            System.out.println("Enter Account Type:\n 1. Savings \n 2. Current");
//            int accountTypeValue = scanner.nextInt();
//            AccountType newAccountType = AccountType.getByValue(accountTypeValue);
//
//            String updateSql = "UPDATE Accounts SET account_type = ? WHERE account_number = ?";
//            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
//                ps.setString(1, newAccountType.getType());
//                ps.setString(2, accountNumber);
//
//                int rows = ps.executeUpdate();
//
//                if (rows > 0) {
//                    System.out.println("Account type updated successfully.");
//                } else {
//                    System.out.println("Failed to update account type. Account not found.");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private boolean isValidPassword(String accountNumber, String password) {
        String storedPassword = getPasswordByNumber(accountNumber);
        return storedPassword != null && storedPassword.equals(password);
    }

    public String getPasswordByNumber(String accountNumber) {
        String query = "SELECT password FROM Accounts where account_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return String.valueOf(resultSet.getInt("password"));
                } else {
                    return "Not valid Id";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateAccountNumber() {
        String characters = "ABCD0123456789";
        int length = 6;
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            char randomChar = characters.charAt(randomIndex);
            accountNumber.append(randomChar);
        }
        return accountNumber.toString();
    }

    public void deleteAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Account PIN: ");
        String password = scanner.nextLine();

        if (isValidPassword(accountNumber, password)) {
            System.out.print("Are you sure you want to delete this account? (Y/N): ");
            String confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")) {

                String deleteSql = "DELETE FROM Accounts WHERE account_number = ?";
                try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
                    ps.setString(1, accountNumber);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Account deleted successfully.");
                    } else {
                        System.out.println("Failed to delete the account.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public double getAccountBalance(String accountNumber) {
        // Define a SQL query to retrieve the account balance
        String selectSql = "SELECT balance FROM Accounts WHERE account_number = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
            ps.setString(1, accountNumber);

            // Execute the query and retrieve the result
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                // Get the balance from the result set
                return resultSet.getDouble("balance");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int getAccountId(String accountNumber) {
        // Define a SQL query to retrieve the account ID
        String selectSql = "SELECT account_id FROM Accounts WHERE account_number = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
            ps.setString(1, accountNumber);

            // Execute the query and retrieve the result
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                   return resultSet.getInt("account_id");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  return -1;
    }
    public static String getAccountNumber() {
        System.out.print("Enter Account Number : ");
        return scanner.nextLine();
    }
}
