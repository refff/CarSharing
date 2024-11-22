package carsharing.menu;

import carsharing.*;

import java.util.List;
import java.util.Scanner;

public class ManagerLoginAction implements MenuAction {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        managerMenu();
    }

    private void managerMenu() {
        int input;

        do {
            System.out.println("""
                    
                    1. Company list\s
                    2. Create a company\s
                    0. Back""");

            input = MainMenu.menuOption();

            switch (input) {
                case 1 -> companyList();
                case 2 -> createCompany();
                case 0 -> {}
                default -> MainMenu.incorrectInput();
            }
        } while (input != 0);
        System.out.println();
    }

    private void companyList() {
        List<Company> companyList = MainMenu.companyDao.findAll();

        if (!companyList.isEmpty()) {
            int option;

            do {
                MainMenu.printElements("\nChoose the company:", companyList);
                System.out.println("0. Back");

                option = MainMenu.menuOption();

                if (option != 0) {
                    companyMenu(option);
                    option = 0;
                }
            } while (option != 0);
        } else {
            MainMenu.emptyListError("company");
        }
    }

    private void companyMenu(int id) {
        int option;
        Company company = MainMenu.companyDao.findById(id);
        System.out.println("\n" + company.getName() + " company:");
        do {
            System.out.println(
                    "1. Car list\n" +
                            "2. Create a car\n" +
                            "0. Back");
            option = MainMenu.menuOption();

            switch (option) {
                case 1 -> carList(company.getId());
                case 2 -> createCar(company.getId());
                case 0 -> {}
                default -> MainMenu.incorrectInput();
            }
        } while (option != 0);
    }

    private void createCompany() {
        System.out.println("\nEnter the company name:");
        String name = scanner.nextLine();
        int id = MainMenu.companyDao.findAll().size();

        MainMenu.companyDao.add(new Company(name, id + 1));

        System.out.println("\nThe company was created!");
    }

    private void carList(int companyId) {
        List<Car> carList = MainMenu.carDao.findAllByCompanyId(companyId);

        if (!carList.isEmpty()) {
            MainMenu.printElements("\nCar list:", carList);
            System.out.println();
        } else {
            MainMenu.emptyListError("car");
            System.out.println();
        }
    }

    private void createCar(int companyId) {
        System.out.println("\nEnter the car name:");
        String carName = scanner.nextLine();
        int id = MainMenu.carDao.findAll().size();

        Car car = new Car(id + 1, carName, companyId);
        MainMenu.carDao.add(car);

        System.out.println("\nThe car was added!\n");
    }
}
