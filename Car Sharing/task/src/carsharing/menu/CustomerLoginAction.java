package carsharing.menu;

import carsharing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomerLoginAction implements MenuAction {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        customerList();
    }

    private void customerList() {
        List<Customer> customerList = MainMenu.customerDao.findAll();

        if (!customerList.isEmpty()) {
            int option;

            MainMenu.printElements("\nChoose a customer:", customerList);
            option = MainMenu.menuOption();

            if (option != 0){
                customerMenu(option);
            }
            System.out.println();
        } else {
            System.out.println("\nThe customer list is empty!\n");
        }
    }

    private void customerMenu(int customerId) {
        int option;

        do {
            System.out.println("""
                
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back""");

            option = MainMenu.menuOption();
            switch (option) {
                case 0 -> {}
                case 1 -> rentCar(customerId);
                case 2 -> returnCar(customerId);
                case 3 -> rentedCar(customerId);
                default -> MainMenu.incorrectInput();
            }
        } while (option != 0);
    }

    private void rentCar(int customerId) {
        int carId = MainMenu.customerDao.findById(customerId).getRentedCarId();

        if (carId != 0) {
            System.out.println("\nYou've already rented a car!");
            return;
        }

        List<Company> companyList = MainMenu.companyDao.findAll();
        if (companyList.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return;
        }

        companyChooseMenu(customerId, companyList);
    }

    private void returnCar(int customerId) {
        int carId = MainMenu.customerDao.findById(customerId).getRentedCarId();

        if (carId == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            MainMenu.customerDao.returnCar(customerId);
            System.out.println("\nYou've returned a rented car!");
        }
    }

    private void rentedCar(int customerId) {
        int carId = MainMenu.customerDao.findById(customerId).getRentedCarId();

        if (carId != 0) {
            int companyId = MainMenu.carDao.findById(carId).getCompanyId();
            System.out.println("\nYour rented car:\n"
                    + MainMenu.carDao.findById(carId) +
                    "\nCompany:\n" +
                    MainMenu.companyDao.findById(companyId));
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }

    private void companyChooseMenu(int customerId, List<Company> companyList) {
        int companyId;
        do {
            MainMenu.printElements("\nChoose a company:", companyList);

            companyId = MainMenu.menuOption();
            if (companyId != 0) {
                List<Car> carList = MainMenu.carDao.findAllNotRented(companyId);

                if (carList.isEmpty()) {
                    System.out.printf("\nNo available cars in the '%s' company\n", MainMenu.companyDao.findById(companyId));
                    return;
                }

                carChooseMenu(customerId, companyId, carList);
                return;
            }
        } while (companyId != 0);
    }

    private void carChooseMenu(int customerId, int companyId, List<Car> carList) {
        MainMenu.printElements("\nChoose a car:", carList);
        int option = MainMenu.menuOption();
        if (option != 0){
            int carId = carId(option, companyId, carList);

            MainMenu.customerDao.rentCar(carId, customerId);
            System.out.println("\nYou rented '" + MainMenu.carDao.findById(carId).getName() + "'");
        }
    }

    private int carId(int menuNum, int companyId, List<Car> list) {
        if (menuNum == 0) return 0;
        String car = list.get(menuNum - 1).toString();
        int id = MainMenu.carDao.findByModel(car);

        return id;
    }

}
