package Service;

import Model.ATM;
import DAO.ATMDao;

import java.util.List;

public class ATMService {
    private static ATMDao atmDao;

    public ATMService() {
        atmDao = new ATMDao();
    }

    public static boolean createATM() {
        return atmDao.createATM();
    }

    public static boolean updateATM() {
        return atmDao.updateATM();
    }

    public static boolean deleteATM() {
        return atmDao.deleteATM();
    }

    public static List<ATM> listATMs() {
        return atmDao.listATMs();
    }

}