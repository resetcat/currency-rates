package io.codelex;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRatesRepository {

    private final String url = "jdbc:mysql://host.docker.internal:3307/currency_rates";
    private final String username = "root";
    private final String password = "misa";
    private Connection connection;

    public CurrencyRatesRepository() throws SQLException {
        createTableIfDoesntExist();
    }

    public void createTableIfDoesntExist() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        String createTableSql = "CREATE TABLE IF NOT EXISTS currency_rates (  date DATE NOT NULL," +
                "  currency VARCHAR(3) NOT NULL, rate DOUBLE PRECISION NOT NULL," +
                "  PRIMARY KEY (date, currency))";
        PreparedStatement createTablesStmnt = connection.prepareStatement(createTableSql);
        createTablesStmnt.execute();
        createTablesStmnt.close();
        connection.close();
    }

    public void addCurrencyRates(List<CurrencyRatesList> list) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);

        String insertSql = "INSERT INTO currency_rates (date, currency, rate) VALUES (?, ?, " +
                "?) ON DUPLICATE KEY UPDATE rate = VALUES(rate)";
        PreparedStatement insertDataStmnt = connection.prepareStatement(insertSql);

        for (CurrencyRatesList currencyRatesList : list) {
            for (Map.Entry<String, Double> entry : currencyRatesList.rates().entrySet()) {
                insertDataStmnt.setDate(1, Date.valueOf(currencyRatesList.date()));
                insertDataStmnt.setString(2, entry.getKey());
                insertDataStmnt.setDouble(3, entry.getValue());
                insertDataStmnt.execute();
            }
        }
        insertDataStmnt.close();
        connection.close();
    }

    public Map<String, Double> getNewest() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        String newestSql = "SELECT currency, rate FROM currency_rates WHERE date = (SELECT MAX(date) FROM currency_rates)";
        PreparedStatement newestDateStnmt = connection.prepareStatement(newestSql);
        ResultSet result = newestDateStnmt.executeQuery();
        Map<String, Double> currencyList = new HashMap<>();
        while (result.next()) {
            currencyList.put(result.getString("currency"),result.getDouble("rate"));
        }
        newestDateStnmt.close();
        result.close();
        connection.close();
        return currencyList;
    }

    public List<String> getCurrency(String currency) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        String currencySQL = "SELECT * FROM currency_rates WHERE currency = ?";
        PreparedStatement currencyStnmt = connection.prepareStatement(currencySQL);
        currencyStnmt.setString(1, currency);
        ResultSet result = currencyStnmt.executeQuery();
        List<String> SingleCurrencyDates = new ArrayList<>();
        while (result.next()) {
            SingleCurrencyDates.add(result.getDate("date") + " " + result.getString("currency") + " : " +
                                       result.getDouble("rate"));
        }
        currencyStnmt.close();
        result.close();
        connection.close();
        return SingleCurrencyDates;
    }


}
