package Model;

public class ATM {
    private int atmId;
    private int bankId;
    private String password;
    private double balance;

    public ATM(int atmId, int bankId, String password, double balance) {
        this.atmId = atmId;
        this.bankId = bankId;
        this.password = password;
        this.balance = balance;
    }

    public ATM(int atmId,int bankId, double balance) {
        this.atmId = atmId;
        this.bankId = bankId;
        this.balance = balance;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public int getBankId() {
        return bankId;
    }



    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "atmId=" + atmId +
                ", bankId=" + bankId +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}