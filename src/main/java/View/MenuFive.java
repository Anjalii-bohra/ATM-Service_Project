package View;

import Service.AccountService;
import Service.CustomerService;

import java.sql.SQLException;

public class MenuFive {
    public void handleCustomerMenu() throws SQLException {
        boolean exit = false;
        Menu menu = new Menu();
        MainMenu mainMenu = new MainMenu();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        while (!exit) {
            menu.displayCustomerMenu();
            int choice = menu.getUserChoice();
            switch (choice) {
                case 1 -> customerService.createCustomer();
                case 2 -> customerService.updateCustomer();
                case 3 -> customerService.deleteCustomer();
                case 4 -> customerService.listCustomers();
                case 5 -> accountService.updateAccountBalance();
                case 6 -> accountService.updateAccountType();
                case 7 -> mainMenu.handleMainMenu();
                case 0 -> {
                    System.out.println("Thanks for using PMC ATM service.");
                    exit = true;
                }
                default -> {
                    System.out.println("Please enter valid option.");
                    handleCustomerMenu();
                }
            }
        }
    }
}
