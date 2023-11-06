package Service;

import DAO.AccountDao;
import DAO.CustomerDao;

public class AccountService {
    private final AccountDao accountDao;
    public AccountService() {
        accountDao = new AccountDao();
    }
    public void updateAccountBalance() {
        accountDao.updateAccountBalance();
    }

    public void updateAccountType() { accountDao.updateAccountType();
    }
}
