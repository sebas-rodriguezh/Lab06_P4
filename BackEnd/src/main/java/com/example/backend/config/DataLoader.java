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
            // Aquí obtenemos la misma y única instancia del Service que usará el Controlador
            Service service = Service.instance();

            try {
                service.create(new Country("Costa Rica", "San José", 5058007, 51100,
                        new ArrayList<>(Arrays.asList(10, -84)), "https://flagcdn.com/cr.svg"));

                service.create(new Country("México", "Ciudad de México", 128932753, 1964375,
                        new ArrayList<>(Arrays.asList(23, -102)), "https://flagcdn.com/mx.svg"));

            } catch (Exception e) {
                System.out.println("DataLoader: Hubo un error al cargar los países (tal vez ya existían).");
            }
        };
    }
}