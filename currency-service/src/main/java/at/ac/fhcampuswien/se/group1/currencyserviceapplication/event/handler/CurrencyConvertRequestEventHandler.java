package at.ac.fhcampuswien.se.group1.currencyserviceapplication.event.handler;

import at.ac.fhcampuswien.se.group1.currencyserviceapplication.event.CurrencyConvertRequestEvent;
import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currency;
import at.ac.fhcampuswien.se.group1.currencyserviceapplication.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CurrencyConvertRequestEventHandler {
    
    private final CurrencyService currencyService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;
    
    @RabbitListener(queues = {"${queue.currency-convert-request}"})
    public Double handleCurrencyConvertRequest(@Payload String payload) {
        log.info("Handling a Currency Convert Request {}", payload);
        
        try {
            CurrencyConvertRequestEvent event = mapper.readValue(payload, CurrencyConvertRequestEvent.class);
            
            log.info("Parsed event CurrencyConvertRequestEvent: {}", event);
            
            Currency currency =
                    currencyService.calculatingCrossCurrency(event.getSourceSymbol(), event.getDestinationSymbol(),
                            event.getSourceValue());
            
            return currency.getRate();
            
        } catch (JsonProcessingException e) {
            log.error("Could not process event json: {}", payload);
        }
        return 0D;
    }
}
