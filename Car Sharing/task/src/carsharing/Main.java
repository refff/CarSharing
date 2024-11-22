package carsharing;

import carsharing.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(args.length == 2 ? args[1] : "dbRandomName");
        int option;

        do {
            mainMenu.displayMenu();
            option = MainMenu.menuOption();
            mainMenu.selectOption(option);
        } while (option != 0);
    }
}