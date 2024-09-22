package com.example.api;

import org.mitre.synthea.engine.Generator;
import org.mitre.synthea.helpers.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

@RestController
class ApiController {

    private static final String LOCAL_OUTPUT_DIR = "./data_output";

    @GetMapping("/")
    public String index() {
        return "Welcome, let's generate some data!";
    }

    @GetMapping("/generate-patients")
    public String generatePatients(
        @RequestParam(
            value = "population",
            defaultValue = "10"
        ) String population
    ) {
        try {
            System.out.println("starting generator.");

            Config.set("generate.default_population", population);
            Config.set("generate.only_alive_patients", "true");
            Config.set("exporter.fhir.use_us_core_ig", "true");
            Config.set("exporter.fhir.us_core_version", "4.1.0");
            Config.set("exporter.baseDirectory", LOCAL_OUTPUT_DIR);

            Generator generator = new Generator();
            generator.run();

            return ("Generated " + population + " patients!");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
