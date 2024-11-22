package carsharing;

import java.util.ArrayList;
import java.util.List;

public interface CarDao {
    List<Car> findAll();
    List<Car> findAllByCompanyId(int id);
    List<Car> findAllNotRented(int companyId);
    Car findById(int id);
    void add(Car car);
    void removeFromCompany(int carId);
    void update(Car car);
    void deleteById(int id);
    void showAll();
    //ArrayList<ArrayList<String>> numOfCars(int companyId);
    int findByModel(String model);
}
