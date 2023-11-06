package Service;

import DAO.BankDao;
import Model.Bank;

import java.util.List;

public class BankService {
    private BankDao bankDao;

    public BankService() {
        bankDao = new BankDao();
    }

    public boolean createBank() {
        return bankDao.createBank();
    }

    public boolean updateBank() {
        return bankDao.updateBank();
    }

    public boolean deleteBank() {
        return bankDao.deleteBank();
    }

    public List<Bank> listBanks() {
        return bankDao.listBanks();
    }
}
