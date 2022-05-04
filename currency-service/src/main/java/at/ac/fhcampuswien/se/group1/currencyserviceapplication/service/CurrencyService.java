package at.ac.fhcampuswien.se.group1.currencyserviceapplication.service;

import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currencies;
import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currency;
import at.ac.fhcampuswien.se.group1.currencyserviceapplication.repository.CurrenciesRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static at.ac.fhcampuswien.se.group1.currencyserviceapplication.utility.CurrencyUtility.loadDocument;
import static at.ac.fhcampuswien.se.group1.currencyserviceapplication.utility.CurrencyUtility.parseDouble;

@Service
public class CurrencyService {
    
    private static final String EURO_SYMBOL = "EUR";
    
    private final CurrenciesRepository currenciesRepository;
    
    public CurrencyService(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }
    
    public List<Currency> getCurrencies() {
        
        List<Currency> currencyList = new ArrayList<>();
        
        Currencies currencies = new Currencies();
        
        try {
            Document doc = loadDocument();
            
            NodeList nodeList = doc.getElementsByTagName("Cube");
            
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    
                    Currency currency = new Currency();
    
                    if (element.getAttribute("currency").isEmpty()) {
                        currency.setCurrencySymbol("N/A");
                    } else {currency.setCurrencySymbol(element.getAttribute("currency"));}
                    
                    currency.setRate(parseDouble(element.getAttribute("rate")));
                    
                    if (!currency.getCurrencySymbol().equals("N/A")) {
                        currencyList.add(currency);
                        
                    }
                }
            }
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        currencyList.add(new Currency("EUR", 1.00));
        currencies.setDate(new Date());
        currencies.setCurrencyList(currencyList);
        currenciesRepository.save(currencies);
        return currencyList;
    }
    
    public Currency currencyPerCode(String symbol) {
        
        List<Currency> currencies = getCurrencies();
        
        return currencies
                .stream()
                .filter(currency1 -> currency1.getCurrencySymbol().equals(symbol))
                .toList()
                .get(0);
    }
    
    public Currency calculatingCrossCurrency(String symbolInput, String symbolOutput, double amount) {
        
        Currency currency = new Currency();
        
        Currency currencyInput = currencyPerCode(symbolInput);
        Currency currencyOutput = currencyPerCode(symbolOutput);
        
        currency.setCurrencySymbol(symbolOutput);
        
        if (symbolInput.equals(EURO_SYMBOL)) {
            currency.setRate(currencyOutput.getRate() * amount);
        } else if (symbolOutput.equals(EURO_SYMBOL)) {
            currency.setRate((1 / currencyInput.getRate()) * amount);
        } else {
            currency.setRate((currencyOutput.getRate() / currencyInput.getRate()) * amount);
        }
        
        return currency;
    }
    
}
