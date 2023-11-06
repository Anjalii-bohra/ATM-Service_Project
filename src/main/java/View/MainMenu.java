package View;

import java.sql.SQLException;

public class MainMenu {
    public void handleMainMenu() throws SQLException {
        Menu menu = new Menu();
        MenuOne One = new MenuOne();
        MenuTwo Two = new MenuTwo();
        MenuThree Three = new MenuThree();
        MenuFour Four = new MenuFour();
        MenuFive Five = new MenuFive();

        boolean exit = false;
        while (!exit) {
            menu.displayMainMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1:
                    One.handleTransactionMenu();
                    break;
                case 2:
                    Two.handleCheckBalanceMenu();
                    break;
                case 3:
                    Three.handleATMMenu();
                    break;
                case 4:
                    Four.handleBankMenu();
                    break;
                case 5:
                    Five.handleCustomerMenu();
                case 0:
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter valid option.");
                    handleMainMenu();
            }
        }
    }

    // Display a message to the user
    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Display an error message to the user
    public void displayError(String errorMessage) {
        System.err.println("Error: " + errorMessage);
    }


}