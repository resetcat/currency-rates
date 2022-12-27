package io.codelex;

import io.codelex.apiresponse.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyRatesService {

    private final CurrencyRatesRepository repository;

    public CurrencyRatesService(CurrencyRatesRepository currencyRatesRepository) {
        this.repository = currencyRatesRepository;
    }

    public CurrencyRatesRepository getRepository() {
        return repository;
    }

    public void getAPIRates() throws MalformedURLException, JAXBException {
        URL url = new URL("https://www.bank.lv/vk/ecb_rss.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Response responseXml = (Response) jaxbUnmarshaller.unmarshal(url);

        repository.addCurrencyRates(responseToList(responseXml));
    }

    private List<CurrencyRatesList> responseToList(Response responseXml) {
        List<CurrencyRatesList> currencyRatesList = new ArrayList<>();
        responseXml.getChannel()
                   .getItems()
                   .forEach(res -> currencyRatesList.add(
                           new CurrencyRatesList(getRates(res.getDescription()),
                                                 getDate(res.getPubDate()))));
        return currencyRatesList;
    }

    private LocalDate getDate(String responseDate) {
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        return LocalDate.parse(responseDate, formatter);
    }

    private Map<String, Double> getRates(String responseDate) {
        Map<String, Double> rates = new HashMap<>();
        Pattern pattern = Pattern.compile("([A-Z]{3})\\s([\\d.]+)");
        Matcher matcher = pattern.matcher(responseDate);
        while (matcher.find()) {
            String countryCode = matcher.group(1);
            double rate = Double.parseDouble(matcher.group(2));
            rates.put(countryCode, rate);
        }
        return rates;
    }


}
