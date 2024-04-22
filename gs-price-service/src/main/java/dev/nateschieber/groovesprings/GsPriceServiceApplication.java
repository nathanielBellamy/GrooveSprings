package dev.nateschieber.groovesprings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GsPriceServiceApplication {

  public static void main(String[] args) {
    System.out.println("======= GS PRICE SERVICE APPLICATION ");
    SpringApplication.run(GsPriceServiceApplication.class, args);
  }
}
