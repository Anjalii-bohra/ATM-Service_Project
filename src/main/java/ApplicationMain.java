import View.MainMenu;

import java.sql.SQLException;

public class ApplicationMain {
    public static void main(String[] args) throws SQLException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.handleMainMenu();
    }
}
