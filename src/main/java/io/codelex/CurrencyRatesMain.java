package io.codelex;


import io.javalin.Javalin;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class CurrencyRatesMain {
    public static void main(String[] args) throws IOException, SQLException, JAXBException {

        CurrencyRatesService service = new CurrencyRatesService(new CurrencyRatesRepository());


        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if (command.equals("update")) {
                service.getAPIRates();
                System.out.println("db updated");
            }
            if (command.equals("start")) {
                Javalin.create()
                       .get("/currency-rates", a -> a.json(service.repository.getNewest()))
                       .get("/currency-rates/{code}",
                            a -> a.json(service.repository.getCurrency(a.pathParam("code"))))
                       .start(7000);
                System.out.println("web app started");
            }
        }


    }

}