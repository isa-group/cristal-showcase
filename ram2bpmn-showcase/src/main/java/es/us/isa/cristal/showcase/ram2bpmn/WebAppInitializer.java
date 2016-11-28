package es.us.isa.cristal.showcase.ram2bpmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * WebAppInitializer
 * Copyright (C) 2016 Universidad de Sevilla
 *
 * @author resinas
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("es.us.isa.cristal.showcase.ram2bpmn")
public class WebAppInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebAppInitializer.class, args);
    }
}
