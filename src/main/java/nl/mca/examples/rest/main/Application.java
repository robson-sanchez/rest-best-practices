package nl.mca.examples.rest.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class
 */
@SpringBootApplication(scanBasePackages = "nl.mca.examples.rest")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
