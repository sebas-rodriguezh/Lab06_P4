package com.example.backend.config;

import com.example.backend.model.Country;
import com.example.backend.service.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Service service = Service.instance();

            try {
                service.create(new Country("Costa Rica", "San José", 5058007, 51100,
                        new ArrayList<>(Arrays.asList(10, -84)), "https://flagcdn.com/cr.svg"));

                service.create(new Country("Panamá", "Ciudad de Panamá", 4218808, 75417,
                        new ArrayList<>(Arrays.asList(9, -79)), "https://flagcdn.com/pa.svg"));

                System.out.println("DataLoader: Países iniciales cargados con éxito.");
            } catch (Exception e) {
                System.out.println("DataLoader: Los países ya existen o hubo un error.");
            }
        };
    }
}