package at.ac.fhcampuswien.se.group1.currencyserviceapplication.utility;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

@Log4j2
public class CurrencyUtility {
    
    private static final String URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    
    private CurrencyUtility() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Document loadDocument() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setNamespaceAware(true);
        
        return factory.newDocumentBuilder().parse(new URL(URL).openStream());
    }
    
    public static double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            return Double.parseDouble(strNumber);
        }
        return 0;
    }
}
