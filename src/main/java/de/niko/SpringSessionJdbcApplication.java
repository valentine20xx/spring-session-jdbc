package de.niko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession
@SpringBootApplication
public class SpringSessionJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionJdbcApplication.class, args);
    }
}