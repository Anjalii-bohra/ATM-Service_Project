package View;

import Service.BankService;

import java.sql.SQLException;

public class MenuFour {
    public void handleBankMenu() throws SQLException {
        boolean exit = false;
        Menu menu = new Menu();
        MainMenu mainMenu = new MainMenu();
        BankService bankService = new BankService();
        while (!exit) {
            menu.displayBankMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1 -> bankService.createBank();
                case 2 -> bankService.updateBank();
                case 3 -> bankService.deleteBank();
                case 4 -> bankService.listBanks();
                case 5 -> mainMenu.handleMainMenu();
                case 0 -> {
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                }
                default -> {
                    System.out.println("Please enter valid option.");
                    handleBankMenu();
                }
            }
        }
    }
}
