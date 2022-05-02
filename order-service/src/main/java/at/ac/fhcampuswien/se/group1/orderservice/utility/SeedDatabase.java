package at.ac.fhcampuswien.se.group1.orderservice.utility;

import at.ac.fhcampuswien.se.group1.orderservice.model.*;
import at.ac.fhcampuswien.se.group1.orderservice.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Component
public class SeedDatabase implements CommandLineRunner {

    private OrderRepository orderRepository;

    public SeedDatabase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {

        OpeningHours openingHoursDatasetOne = new OpeningHours(1, "06.00 Uhr - 23.30 Uhr",
                "08.00 Uhr - 23.30 Uhr",
                "10.00 Uhr - 23.30 Uhr",
                "11.00 Uhr - 23.30 Uhr",
                "07.30 Uhr - 23.30 Uhr",
                "06.00 Uhr - 20.00 Uhr",
                "05.00 Uhr - 23.00 Uhr");

        Location locationDatasetOne =
                new Location(1, openingHoursDatasetOne, "Airport Vienna", "Parkstrasse", "16", "Schwechat", 1300,
                        "airport-vienna@carrentalvienna.com", "06602526284", "48.12037524536211", "16.563466629953894",
                        SagaStatus.FINISHED);

        Car carDatasetOne =
                new Car(
                        BigInteger.valueOf(1),
                        CarStatus.AVAILABLE,
                        "W0L000041T212345678890",
                        "Audi",
                        LocalDate.now().minusYears(10),
                        "Black",
                        "TT",
                        "Coupé",
                        "Diesel",
                        BigDecimal.ONE,
                        300,
                        "Hybrid",
                        "automatic",
                        false,
                        2,
                        65000D,
                        CurrencySymbol.USD,
                        "https://www.auto-data.net/en/audi-tt-rs-roadster-8s-facelift-2019-generation-7105#image8"
                );

        Order orderDatasetOne = new Order(BigInteger.valueOf(1),carDatasetOne,locationDatasetOne,locationDatasetOne, LocalDate.now(),LocalDate.now(),LocalDate.now(),Payment.AMERICAN_EXPRESS,
                "3234234234234", "876", new Date(),"Max","Muster","max@email.com",OrderStatus.CREATED,SagaStatus.FINISHED);

        this.orderRepository.deleteAll();

        this.orderRepository.save(orderDatasetOne);

    }
}
