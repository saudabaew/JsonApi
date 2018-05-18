package rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSourceUtil {

    @Autowired
    private Environment env;

    //возвращает объект подключения к БД Sakura
    public DataSource getDataSourceSakura() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.sakura"));
        dataSource.setUrl(env.getProperty("spring.datasource.url.sakura"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.sakura"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.sakura"));
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        System.out.println("## Sakura: " + dataSource);
        return dataSource;
    }

    //возвращает объект подключения к БД Agrotronic
    public DataSource getDataSourceAgrotronic() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.agrotronic"));
        dataSource.setUrl(env.getProperty("spring.datasource.url.agrotronic"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.agrotronic"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.agrotronic"));
        System.out.println("## Agrotronic: " + dataSource);
        return dataSource;
    }
}
