package com.teammental.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Config Sınıfı
 */

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
