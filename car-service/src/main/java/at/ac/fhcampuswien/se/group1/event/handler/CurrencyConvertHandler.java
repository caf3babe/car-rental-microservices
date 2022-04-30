package at.ac.fhcampuswien.se.group1.event.handler;

import at.ac.fhcampuswien.se.group1.event.CurrencyConvertRequestEvent;
import at.ac.fhcampuswien.se.group1.models.CurrencySymbol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class CurrencyConvertHandler {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Value("${queue.currency-convert-request}") String queue;
    
    public Double convert(CurrencySymbol inCurrencySymbol, Double price, CurrencySymbol outCurrencySymbol)
            throws JsonProcessingException {
        
        CurrencyConvertRequestEvent event = new CurrencyConvertRequestEvent(outCurrencySymbol.getValue(), inCurrencySymbol.getValue(), price);
        log.info("Sending event {} to queue {}", event, queue);
        
        Double convertedCurrency = (Double) rabbitTemplate.convertSendAndReceive(queue, objectMapper.writeValueAsString(event));
        
        if ( convertedCurrency == null ) {
            log.error("Currency Converter Service currently not available");
        }
    
        assert convertedCurrency != null;
        
        return (double) convertedCurrency.intValue();
    }
}
