package View;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    //    Input from the user
    public int getUserChoice(){
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.next());
    }

    // Main Menu
    public void displayMainMenu() {
        System.out.println("===============================================================");
        System.out.println(" Welcome to PMC ATM Service ");
        System.out.println("===============================================================");
        System.out.println("\nMain Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Transaction");
        System.out.println("2. Check Balance");
        System.out.println("3. Manage ATM Details");
        System.out.println("4. Manage Bank Details");
        System.out.println("5. Manage Customer Details");
        System.out.println("0. Exit");

    }

    // Transaction menu
    public void displayTransactionMenu() {
        System.out.println("===============================================================");
        System.out.println("\nTransaction Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Back to Main Menu");
        System.out.println("0. Exit");
    }

    // Display the Check Balance menu
    public void displayCheckBalanceMenu() {
        System.out.println("===============================================================");
        System.out.println("\nCheck Balance Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Check Account Balance");
        System.out.println("2. Check ATM Balance");
        System.out.println("3. Back to Main Menu");
        System.out.println("0. Exit");
    }

    // ATM details
    public void displayATMMenu() {
        System.out.println("===============================================================");
        System.out.println("\nATM Details Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Create ATM");
        System.out.println("2. Update ATM");
        System.out.println("3. Delete ATM");
        System.out.println("4. List ATM");
        System.out.println("5. Back to Main Menu");
        System.out.println("0. Exit");

    }

    // Bank details
    public void displayBankMenu() {
        System.out.println("===============================================================");
        System.out.println("\nBank Details Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Create Bank");
        System.out.println("2. Update Bank");
        System.out.println("3. Delete Bank");
        System.out.println("4. List Bank");
        System.out.println("5. Back to Main Menu");
        System.out.println("0. Exit");

    }

    // Customer details
    public void displayCustomerMenu() {
        System.out.println("===============================================================");
        System.out.println("\nCustomer Details Menu:");
        System.out.println("===============================================================");
        System.out.println("1. Create Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. List Customer");
        System.out.println("5. Update Account Balance");
        System.out.println("6. Update Account Type(Saving/Current)");
        System.out.println("7. Back to Main Menu");
        System.out.println("0. Exit");

    }

}