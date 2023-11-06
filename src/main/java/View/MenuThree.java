package View;

import Service.ATMService;

import java.sql.SQLException;

public class MenuThree {
     void handleATMMenu() throws SQLException {
        boolean exit = false;
         Menu menu = new Menu();
         MainMenu mainMenu = new MainMenu();
         ATMService atmService = new ATMService();
        while (!exit) {
            menu.displayATMMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1 -> atmService.createATM();
                case 2 -> atmService.updateATM();
                case 3 -> atmService.deleteATM();
                case 4 -> atmService.listATMs();
                case 5 -> mainMenu.handleMainMenu();
                case 0 -> {
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                }
                default -> {
                    System.out.println("Please enter valid option.");
                    handleATMMenu();
                }
            }
        }
    }
}
