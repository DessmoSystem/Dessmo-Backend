package pe.edu.upc.dessmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DessmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DessmoApplication.class, args);
    }

}
