package Model;

import java.sql.Date;

public class Account {
    private int accountId;
    private int customerId;
    private String accountNumber;
    private AccountType accountType;
    private double balance;
    private int password;
    private Date createdAt;


    public Account(int accountId, int customerId, String accountNumber, AccountType accountType, double balance, Date createdAt) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = AccountType.valueOf(String.valueOf(accountType));
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account() {

    }




    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int getPassword() {
        return password;
    }

    public void setpassword(int password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", customerId=" + customerId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                '}';
    }
}