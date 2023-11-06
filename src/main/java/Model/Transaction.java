package Model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Transaction {
    private  int transactionId;
    private  int accountId;
    private  TransactionType transactionType;
    private  double amount;
    private  Date transactionDate;
    public Transaction(int transactionId, int accountId, TransactionType transactionType, double amount, Date transactionDate) {
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }
}