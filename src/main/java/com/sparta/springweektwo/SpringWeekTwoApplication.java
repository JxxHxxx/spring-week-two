package com.sparta.springweektwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringWeekTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWeekTwoApplication.class, args);
    }

}
