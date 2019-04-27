package com.weathertracker.measurements;

import com.weathertracker.measurements.Measurement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.HashMap;

@Configuration
public class Config {

    @Bean
    public HashMap<ZonedDateTime, Measurement> getMeasurementMap() {
        return new HashMap<>();
    }
}
