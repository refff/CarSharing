package carsharing;

import java.util.ArrayList;
import java.util.List;

public class DbCarDao implements CarDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR UNIQUE NOT NULL, " +
            "COMPANY_ID INTEGER NOT NULL, " +
            "CONSTRAINT FK_ID FOREIGN KEY (COMPANY_ID) " +
            "REFERENCES COMPANY(ID));";
    private static final String SELECT_ALL = "Select * from car;";
    private static final String SELECT_ALL_BY_ID = "Select * from car where COMPANY_ID = %d;";
    private static final String SELECT_ALL_NOT_RENTED = "Select car.id, car.name, car.company_id from car " +
            "left join customer on car.id = CUSTOMER.RENTED_CAR_ID " +
            "where CUSTOMER.RENTED_CAR_ID IS NULL and company_id = %d;";
    private static final String SELECT = "Select * from car where id = %d;";
    private static final String REMOVE_FROM_COMPANY = "UPDATE CAR SET COMPANY_ID = 0 WHERE ID = %d";
    private static final String INSERT_DATA = "INSERT INTO car VALUES (%d, '%s', %d);";
    private static final String UPDATE_DATA = "update car set name = %s where id = %d;";
    private static final String DELETE_DATA = "delete from car where id = %d;";

    private final DbClient dbClient;

    public DbCarDao(String url) {
        dbClient = new DbClient(url);
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Car> findAll() {
        List<Car> carList = dbClient.selectForList(SELECT_ALL, "Car");
        return carList;
    }

    @Override
    public Car findById(int id) {
        Car car = (Car) dbClient.select(String.format(SELECT, id), "Car");
        return car;
    }

    public List<Car> findAllNotRented(int companyId) {
        List<Car> carList = dbClient.selectForList(String.format(SELECT_ALL_NOT_RENTED, companyId), "Car");
        return carList;
    }

    public List<Car> findAllByCompanyId(int id) {
        List<Car> carList = dbClient.selectForList(String.format(SELECT_ALL_BY_ID, id), "Car");
        return carList;
    }

    @Override
    public void add(Car car) {
        dbClient.run(String.format(INSERT_DATA, car.getId(), car.getName(), car.getCompanyId()));
    }

    @Override
    public void update(Car car) {
        dbClient.run(String.format(UPDATE_DATA, car.getId(), car.getName()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }

    @Override
    public void removeFromCompany(int carId) {
        dbClient.run(String.format(REMOVE_FROM_COMPANY, carId));
    }

    @Override
    public void showAll() {
        dbClient.selectAllCar(SELECT_ALL);
    }

    @Override
    public int findByModel(String model) {
        Car car = (Car) dbClient.select(String.format("select * from car where name = '%s';", model), "Car");
        return car.getId();
    }
}
