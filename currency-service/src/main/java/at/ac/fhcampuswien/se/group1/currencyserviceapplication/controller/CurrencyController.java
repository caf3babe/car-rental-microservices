package at.ac.fhcampuswien.se.group1.currencyserviceapplication.controller;

import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currency;
import at.ac.fhcampuswien.se.group1.currencyserviceapplication.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "currency", description = "the currency API")
@RestController
@RequestMapping("/api/v1")
public class CurrencyController {
    
    private final CurrencyService currencyService;
    
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    /**
     * GET /currency : Get a list of supported currencies
     *
     * @return Successful Operation (status code 200)
     */
    @Operation(operationId = "getCurrencies", summary = "Get a list of currencies", tags = {"currency"},
            responses = {@ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    [{"symbol": "USD",
                                        "rate": 1.1005
                                      },
                                      {
                                        "symbol": "JPY",
                                        "rate": 135.08
                                      },
                                      {
                                        "symbol": "BGN",
                                        "rate": 1.9558
                                      },
                                      {
                                        "symbol": "CZK",
                                        "rate": 24.32
                                      },
                                      {
                                        "symbol": "DKK",
                                        "rate": 7.4385
                                      },
                                      {
                                        "symbol": "GBP",
                                        "rate": 0.8389
                                      },
                                      {
                                        "symbol": "HUF",
                                        "rate": 369.15
                                      },
                                      {
                                        "symbol": "PLN",
                                        "rate": 4.6375
                                      },
                                      {
                                        "symbol": "RON",
                                        "rate": 4.9432
                                      },
                                      {
                                        "symbol": "SEK",
                                        "rate": 10.3849
                                      },
                                      {
                                        "symbol": "CHF",
                                        "rate": 1.0203
                                      },
                                      {
                                        "symbol": "ISK",
                                        "rate": 141.8
                                      },
                                      {
                                        "symbol": "NOK",
                                        "rate": 9.5489
                                      },
                                      {
                                        "symbol": "HRK",
                                        "rate": 7.5455
                                      },
                                      {
                                        "symbol": "TRY",
                                        "rate": 16.183
                                      },
                                      {
                                        "symbol": "AUD",
                                        "rate": 1.4651
                                      },
                                      {
                                        "symbol": "BRL",
                                        "rate": 5.1162
                                      },
                                      {
                                        "symbol": "CAD",
                                        "rate": 1.3749
                                      },
                                      {
                                        "symbol": "CNY",
                                        "rate": 7.0026
                                      },
                                      {
                                        "symbol": "HKD",
                                        "rate": 8.6226
                                      },
                                      {
                                        "symbol": "IDR",
                                        "rate": 15783.89
                                      },
                                      {
                                        "symbol": "ILS",
                                        "rate": 3.5312
                                      },
                                      {
                                        "symbol": "INR",
                                        "rate": 83.118
                                      },
                                      {
                                        "symbol": "KRW",
                                        "rate": 1338.41
                                      },
                                      {
                                        "symbol": "MXN",
                                        "rate": 21.82
                                      },
                                      {
                                        "symbol": "MYR",
                                        "rate": 4.643
                                      },
                                      {
                                        "symbol": "NZD",
                                        "rate": 1.586
                                      },
                                      {
                                        "symbol": "PHP",
                                        "rate": 56.521
                                      },
                                      {
                                        "symbol": "SGD",
                                        "rate": 1.4938
                                      },
                                      {
                                        "symbol": "THB",
                                        "rate": 36.894
                                      },
                                      {
                                        "symbol": "ZAR",
                                        "rate": 16.0957
                                      },
                                      {
                                        "symbol": "EUR",
                                        "rate": 1.00
                                      }
                                    ]
                                    """)
                    },
                            array = @ArraySchema(schema = @Schema(implementation = Currency.class)))
            })})
    @GetMapping(value = "/currency", produces = {"application/json"})
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok(currencyService.getCurrencies());
    }
    
    @GetMapping(value = "/currency/{symbol}", produces = {"application/json"})
    public ResponseEntity<Currency> getCurrencyBySymbol(
            @Parameter(name = "symbol", description = "The symbol of the currency to retrieve", required = true)
            @PathVariable("symbol")
                    String symbol) {
        return ResponseEntity.ok(currencyService.currencyPerCode(symbol));
        
    }
    
    @GetMapping(value = "/currency/calculate", produces = {"application/json"})
    public ResponseEntity<Currency> calculatingCrossCurrency(
            @RequestParam String symbolInput, @RequestParam String symbolOutput, @RequestParam double amount) {
        return ResponseEntity.ok(currencyService.calculatingCrossCurrency(symbolInput, symbolOutput, amount));
    }
}
