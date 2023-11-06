package DAO;

import Model.Bank;
import Util.DatabaseConnectionManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankDao {
    static BasicDataSource dataSource = DatabaseConnectionManager.getDataSource();
    static Connection connection ;
    static Scanner scanner;

    public BankDao() {
        // Create a connection using the DatabaseConnectionManager
        try {
            connection = dataSource.getConnection();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createBank() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false; // Return  an error
        }

        System.out.print("Enter the bank name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the bank Password: ");
        String password = scanner.nextLine();

        String insertSql = "INSERT INTO Bank (name, password) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, password);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newBankId = generatedKeys.getInt(1);
                    System.out.println("New Bank Created");
                    System.out.println("Your new Bank Id is " + newBankId);
                    return true;
                } else {
                    System.out.println("Failed to retrieve the new Bank ID");
                }
            } else {
                System.out.println("Failed to create a new bank.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return  an error
    }


    public boolean updateBank() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false;
        }
        System.out.print("Enter Bank ID: ");
        int bankId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(bankId, password)) {
            System.out.println("Logged In ");

            System.out.print("Enter New Name: ");
            String newName = scanner.nextLine();

            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();

            String updateSql = "UPDATE Bank SET name = ?, password = ? WHERE bank_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setString(1, newName);
                ps.setString(2, newPassword);
                ps.setInt(3, bankId);
                int rows = ps.executeUpdate();
                System.out.println("Bank Updated");
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Check the Password or Id again");
        return false;
    }

    public boolean deleteBank() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
        }

        System.out.print("Enter Bank ID: ");
        int bankId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(bankId, password)) {
            System.out.println("Logged In ");

            String deleteSql = "DELETE FROM Bank WHERE bank_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
                ps.setInt(1, bankId);
                ps.executeUpdate();
                System.out.println("Bank Deleted");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else { System.out.print("Check the Password or Id again");}
        return false;
    }

    public List<Bank> listBanks() {
        List<Bank> bankList = new ArrayList<>();

        String selectSql = "SELECT * FROM Bank";

        try (PreparedStatement ps = connection.prepareStatement(selectSql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                int bankId = resultSet.getInt("bank_id");
                String name = resultSet.getString("name");
                Bank bank = new Bank(bankId, name);
                bankList.add(bank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!bankList.isEmpty()) {
            System.out.println("List of Banks:");
            for (Bank bank : bankList) {
                System.out.println("Bank ID: " + bank.getBankId() + ", Name: " + bank.getName());
            }
        } else {
            System.out.println("No banks found.");
        }
        return bankList;
    }


    public boolean isValidPassword(int id, String password) {
        String storedPassword = getPasswordById(id);
        return storedPassword != null && storedPassword.equals(password);
    }

    public String getPasswordById(int id) {
        String query = "SELECT password FROM Bank where bank_Id = ?";
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