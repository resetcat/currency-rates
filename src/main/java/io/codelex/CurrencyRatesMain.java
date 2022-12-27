package io.codelex;


import io.javalin.Javalin;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Scanner;

public class CurrencyRatesMain {
    public static void main(String[] args) throws IOException, JAXBException {

        CurrencyRatesService service = new CurrencyRatesService(new CurrencyRatesRepository());


        Scanner scan = new Scanner(System.in);
        if (scan.nextLine().equals("update")) {
            service.getAPIRates();
        }
        if (scan.nextLine().equals("start")) {
            Javalin.create()
                   .get("/currency-rates", a -> a.json(service.getRepository().getNewest()))
                   .get("/currency-rates/{code}",
                        a -> a.json(service.getRepository().getCurrency(a.pathParam("code"))))
                   .start(7000);
        }


    }

}