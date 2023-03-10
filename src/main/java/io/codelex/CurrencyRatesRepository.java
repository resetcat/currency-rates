package io.codelex;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRatesRepository {

    public CurrencyRatesRepository() throws SQLException {
        createTableIfDoesntExist();
    }

    public void createTableIfDoesntExist() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS currency_rates (  date DATE NOT NULL," +
                "  currency VARCHAR(3) NOT NULL, rate DOUBLE PRECISION NOT NULL," +
                "  PRIMARY KEY (date, currency))";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement createTablesStmnt = connection.prepareStatement(createTableSql)) {
            createTablesStmnt.execute();
        }
    }

    public void addCurrencyRates(List<CurrencyRatesList> list) throws SQLException {
        String insertSql = "INSERT INTO currency_rates (date, currency, rate) VALUES (?, ?, " +
                "?) ON DUPLICATE KEY UPDATE rate = VALUES(rate)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement insertDataStmnt = connection.prepareStatement(
                insertSql)) {
            for (CurrencyRatesList currencyRatesList : list) {
                for (Map.Entry<String, Double> entry : currencyRatesList.rates().entrySet()) {
                    insertDataStmnt.setDate(1, Date.valueOf(currencyRatesList.date()));
                    insertDataStmnt.setString(2, entry.getKey());
                    insertDataStmnt.setDouble(3, entry.getValue());
                    insertDataStmnt.execute();
                }
            }
        }
    }

    public Map<String, Double> getNewest() throws SQLException {
        String newestSql = "SELECT currency, rate FROM currency_rates WHERE date = (SELECT MAX(date) FROM currency_rates)";
        Map<String, Double> currencyList = new HashMap<>();
       try(Connection connection = HikariCPDataSource.getConnection();
        PreparedStatement newestDateStnmt = connection.prepareStatement(newestSql);
        ResultSet result = newestDateStnmt.executeQuery()) {
           while (result.next()) {
               currencyList.put(result.getString("currency"), result.getDouble("rate"));
           }
       }
        return currencyList;
    }

    public List<String> getCurrency(String currency) throws SQLException {
        String currencySQL = "SELECT * FROM currency_rates WHERE currency = ?";
        List<String> singleCurrencyDates = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
        PreparedStatement currencyStnmt = connection.prepareStatement(currencySQL)) {

        currencyStnmt.setString(1, currency);
        ResultSet result = currencyStnmt.executeQuery();
        while (result.next()) {
            singleCurrencyDates.add(
                    result.getDate("date") + " " + result.getString("currency") + " : " +
                            result.getDouble("rate"));

        }}
        return singleCurrencyDates;
    }


}
