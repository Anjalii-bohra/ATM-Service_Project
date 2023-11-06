package DAO;

import Model.Transaction;
import Model.TransactionType;

import Util.DatabaseConnectionManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TransactionDao {
    static BasicDataSource dataSource = DatabaseConnectionManager.getDataSource();
    static Connection connection;
    static Scanner scanner;
    static AccountDao accountDao ;

    public TransactionDao() throws SQLException {
        if (dataSource != null) {
            connection = dataSource.getConnection();
            scanner = new Scanner(System.in);
            accountDao = new AccountDao();
        } else {
            System.out.println("Database connection pool not initialized.");
        }
    }

    public static boolean createTransaction(String accountNumber, double newBalance, TransactionType transactionType) {
        if (connection == null) {
            System.out.println("NO CONNECTION");
            return false;
        }
        int accountId = accountDao.getAccountId(accountNumber);

        String insertSql = "INSERT INTO Transactions (account_id, amount, transaction_type) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, accountId);
            ps.setDouble(2, newBalance);
            ps.setString(3, transactionType.getDisplayName());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> listTransactions() {
        List<Transaction> transactionList = new ArrayList<>();

        String accountNumber = getAccountNumber();
        int accountId = accountDao.getAccountId(accountNumber);

        String selectSql = "SELECT * FROM Transactions WHERE account_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int transactionId = resultSet.getInt("transactionId");
                    TransactionType transactionType = TransactionType.valueOf(resultSet.getString("transactionType"));
                    double amount = resultSet.getDouble("amount");
                    Date transactionDate = resultSet.getDate("transactionDate");
                    Transaction transaction = new Transaction(transactionId, accountId, transactionType, amount, transactionDate);
                    transactionList.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!transactionList.isEmpty()) {
            System.out.println("List of Transaction: ");
            for (Transaction transactions : transactionList)
                System.out.println("Transaction ID: " + transactions.getTransactionId() + ", Transaction Type: " + transactions.getTransactionType() +
                        ", Amount: " + transactions.getAmount() + ", Transaction Date: " + transactions.getTransactionDate());
        } else {
            System.out.println("No Transactions Made");
        }
        return transactionList;
    }

    public static boolean Deposit() {
        try {
            connection.setAutoCommit(false);

            String accountNumber = getAccountNumber();

            double currentBalance = accountDao.getAccountBalance(accountNumber);

            // Get the amount to be deposited
            double depositAmount = getDepositAmount();

            // Calculate the new balance after the deposit.
            double newBalance = currentBalance + depositAmount;

            // Update the account balance in the database.
            String updateSql = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setDouble(1, newBalance);
                ps.setString(2, accountNumber);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    boolean transactionCreated = createTransaction(accountNumber, newBalance, TransactionType.DEPOSIT);
                    if (transactionCreated) {
                        // Commit the transaction.
                        connection.commit();
                        return true;
                    } else {
                        System.out.println("Failed to create a transaction.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback the transaction in case of an error.
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                // Set auto-commit back to true.
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
        return false;
    }

    public static void Withdraw() {
        try {
            // Begin a transaction if your database supports it.
            connection.setAutoCommit(false);

            //Get Account Number
            String accountNumber = getAccountNumber();

            // Check if the account exists and fetch its current balance.
            double currentBalance = accountDao.getAccountBalance(accountNumber);

            // Get the amount to be withdrawn
            double withdrawAmount = getWithdrawalAmount();

            // Check if the account has sufficient balance for the withdrawal.
            if (currentBalance < withdrawAmount) {
                // Insufficient balance, rollback the transaction and return false.
                connection.rollback();
            }

            // Calculate the new balance after the withdrawal.
            double newBalance = currentBalance - withdrawAmount;

            // Update the account balance in the database.
            String updateSql = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setDouble(1, newBalance);
                ps.setString(2, accountNumber);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    boolean transactionCreated = createTransaction(accountNumber, newBalance, TransactionType.WITHDRAWAL);
                    if (transactionCreated) {
                        // Commit the transaction.
                        connection.commit();
                    } else {
                        System.out.println("Failed to create a transaction.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback the transaction in case of an error.
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                // Set auto-commit back to true.
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }

    }

    // Get the deposit amount from the user
    public static double getDepositAmount() {
        System.out.print("Enter deposit amount: $");
        return Double.parseDouble(scanner.nextLine());
    }

    // Get the withdrawal amount from the user
    public static double getWithdrawalAmount() {
        System.out.print("Enter withdrawal amount: $");
        return Double.parseDouble(scanner.nextLine());
    }

    public static String getAccountNumber() {
        System.out.print("Enter Account Number : ");
        return scanner.nextLine();
    }
}