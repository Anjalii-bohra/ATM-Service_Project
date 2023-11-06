package View;

import DAO.ATMDao;
import DAO.AccountDao;

import java.sql.SQLException;

public class MenuTwo {
    void handleCheckBalanceMenu() throws SQLException {
        boolean exit = false;
        Menu menu = new Menu();
        MainMenu mainMenu = new MainMenu();
        AccountDao accountDao = new AccountDao();
        ATMDao atmDao = new ATMDao();
        while (!exit) {
            menu.displayCheckBalanceMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1 -> {
                    String accountNumber = AccountDao.getAccountNumber();
                    System.out.println("Account Balance is "+ accountDao.getAccountBalance(accountNumber));
                }
                case 2 -> atmDao.checkATMBalance();
                case 3 -> mainMenu.handleMainMenu();
                case 0 -> {
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                }
                default -> {
                    System.out.println("Please enter valid option.");
                    handleCheckBalanceMenu();
                }
            }
        }
    }
}
