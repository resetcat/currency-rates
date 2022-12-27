package io.codelex;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        Properties prop = new Properties();
        try(InputStream inputStream = new FileInputStream("/app/config/database.properties")){
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.setJdbcUrl(prop.getProperty("database.url"));
        config.setUsername(prop.getProperty("database.username"));
        config.setPassword(prop.getProperty("database.password"));
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private HikariCPDataSource() {
    }
}
