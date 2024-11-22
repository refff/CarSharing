package carsharing;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public class DbCompanyDao implements CompanyDao {
    private static final String CREATE_TABLE = "Create table IF NOT EXISTS company " +
            "(id integer primary key AUTO_INCREMENT, " +
            "name varchar not null unique);";
    private static final String SELECT_ALL = "Select * from company;";
    private static final String SELECT = "Select * from company where id = %d;";
    private static final String INSERT_DATA = "INSERT INTO company VALUES (%d, '%s');";
    private static final String UPDATE_DATA = "update company set name = %s where id = %d;";
    private static final String DELETE_DATA = "delete from company where id = %d;";

    private final DbClient dbClient;

    public DbCompanyDao(String url) {
        dbClient = new DbClient(url);
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Company> findAll() {
        return dbClient.selectForList(SELECT_ALL, "Company");
    }

    @Override
    public Company findById(int id) {
        Company company = (Company) dbClient.select(String.format(SELECT, id), "Company");
        return company;
    }

    @Override
    public void add(Company company) {
        dbClient.run(String.format(INSERT_DATA, company.getId(), company.getName()));
    }

    @Override
    public void update(Company company) {
        dbClient.run(String.format(UPDATE_DATA, company.getName(), company.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }

    @Override
    public void showAll() {
        dbClient.selectAllComp(SELECT_ALL);
    }
}
