package io.codelex;

import java.time.LocalDate;
import java.util.Map;

public record CurrencyRatesList(Map<String, Double> rates, LocalDate date) {
}
