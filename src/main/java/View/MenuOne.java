package View;

import DAO.TransactionDao;

import java.sql.SQLException;

public class MenuOne {
    public void handleTransactionMenu() throws SQLException {
        boolean exit = false;
        Menu menu = new Menu();
        MainMenu mainMenu = new MainMenu();
        while (!exit) {
            menu.displayTransactionMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1 -> TransactionDao.Deposit();
                case 2 -> TransactionDao.Withdraw();
                case 3 -> mainMenu.handleMainMenu();
                case 0 -> {
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                }
                default -> {
                    System.out.println("Please enter valid option.");
                    handleTransactionMenu();
                }
            }
        }
    }
}
