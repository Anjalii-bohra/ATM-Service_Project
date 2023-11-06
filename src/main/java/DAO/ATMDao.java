package DAO;

import Model.*;
import Util.DatabaseConnectionManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMDao {
    static BasicDataSource dataSource = DatabaseConnectionManager.getDataSource();
    static Connection connection = null;
    static Scanner scanner;

    public ATMDao() {
        // Create a connection using the DatabaseConnectionManager
        try {
            connection = dataSource.getConnection();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createATM() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false; // Return an error
        }

        System.out.print("Enter Bank ID : ");
        int bankId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the ATM Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter the Total Balance in ATM: ");
        double balance = Double.parseDouble(scanner.nextLine());

        String insertSql = "INSERT INTO ATM (bank_id, password, balance) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bankId);
            ps.setString(2, password);
            ps.setDouble(3, balance);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newATMId = generatedKeys.getInt(1);
                    System.out.println("New ATM Created");
                    System.out.println("Your new ATM Id is " + newATMId);
                    return true;
                } else {
                    System.out.println("Failed to retrieve the new ATM ID");
                }
            } else {
                System.out.println("Failed to create a new ATM.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return an error
    }

//    public boolean updateATM() {
//        if (connection == null) {
//            System.out.println("NO CONNECTION");
//            return false;
//        }
//        System.out.print("Enter ATM ID: ");
//        int atmId = Integer.parseInt(scanner.nextLine());
//
//        System.out.print("Enter Password: ");
//        String password = scanner.nextLine();
//
//        if (isValidPassword(atmId, password)) {
//            System.out.println("Logged In ");
//
//            System.out.print("Enter New Password: ");
//            String newPassword = scanner.nextLine();
//
//            System.out.print("Enter the Balance: ");
//            String balance = scanner.nextLine();
//
//            String updateSql = "UPDATE ATM SET password = ? balance = ? WHERE atm_id = ?";
//            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
//                ps.setString(1, newPassword);
//                ps.setInt(2, Integer.parseInt(balance));
//                ps.setInt(3, atmId);
//                int rows = ps.executeUpdate();
//                System.out.println("ATM Updated");
//                return rows > 0;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.print("Check the Password or Id again");
//        return false;
//    }

    public boolean updateATM() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false;
        }
        System.out.print("Enter ATM ID: ");
        int atmId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(atmId, password)) {
            System.out.println("Logged In ");

            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();

            System.out.print("Enter the Balance: ");
            String balanceInput = scanner.nextLine();

            // Validate and parse balance input
            double balance = 0.0;
            try {
                balance = Double.parseDouble(balanceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance input. Please enter a valid number.");
                return false;
            }

            String updateSql = "UPDATE ATM SET password = ?, balance = ? WHERE atm_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setString(1, newPassword);
                ps.setDouble(2, balance);
                ps.setInt(3, atmId);
                int rows = ps.executeUpdate();
                System.out.println("ATM Updated");
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Check the Password or ID again");
        return false;
    }

    public  boolean deleteATM() {
        if (connection == null) {
            System.out.println("NO CONNECTION");
        }
        System.out.print("Enter ATM ID: ");
        int atmId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(atmId, password)) {
            System.out.print("Logged In ");

            String deleteSql = "DELETE FROM ATM WHERE atm_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
                ps.setInt(1, atmId);
                int rows = ps.executeUpdate();
                System.out.println("ATM Deleted");
                return rows > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Check the Password or Id again");

        return false;
    }

    public List<ATM> listATMs() {
        List<ATM> atmList = new ArrayList<>();
        String selectSql = "SELECT * FROM ATM";
        try (PreparedStatement ps = connection.prepareStatement(selectSql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                int atmId = resultSet.getInt("atm_id");
                int bankId = resultSet.getInt("bank_id");
                double balance = resultSet.getDouble("balance");
                ATM atm = new ATM(atmId, bankId, balance);
                atmList.add(atm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!atmList.isEmpty()) {
            System.out.println("List of ATMs:");
            for (ATM atm : atmList) {
                System.out.println("ATM ID: " + atm.getAtmId() + ", Bank ID: " + atm.getBankId() + ", Balance: " + atm.getBalance());
            }
        } else {
            System.out.println("No ATMs found.");
        }

        return atmList;
    }

    public void checkATMBalance(){
        if (connection == null) {
            System.out.println("NO CONNECTION");
        }
        System.out.print("Enter ATM ID: ");
        int atmId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (isValidPassword(atmId, password)){
            System.out.print("Logged In ");

            String selectSql = "SELECT balance FROM ATM WHERE atm_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectSql);) {
                ps.setInt(1, atmId);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    double balance = resultSet.getDouble("balance");
                    System.out.println("ATM " +atmId+ " Balance:" +balance);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public  boolean isValidPassword(int id, String password) {

        String storedPassword = getPasswordById(id);

        if (storedPassword != null && storedPassword.equals(password)) {
            return true;
        }
        return false;
    }

    public String getPasswordById(int id) {
        String query = "SELECT password FROM ATM where atm_Id = ?";
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