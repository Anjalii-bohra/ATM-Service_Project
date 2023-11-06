package Service;

import DAO.TransactionDao;
import Model.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;

    public TransactionService() throws SQLException {
        transactionDao = new TransactionDao();
    }

    public List<Transaction> listTransactions() {
        return transactionDao.listTransactions();
    }

}