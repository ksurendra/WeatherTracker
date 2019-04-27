package com.weathertracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * The priamry sping boot application class
 */
@SpringBootApplication
public class WeatherTrackerApplication implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
  private static final int DEFAULT_PORT = 8000; // this is what HackerRank expects

  public static void main(String[] args) {
    SpringApplication.run(WeatherTrackerApplication.class, args);
  }

  @Override
  public void customize(TomcatServletWebServerFactory factory) {
    factory.setPort(getServerPort());
  }

  private int getServerPort() {
    try {
      return Integer.parseInt(System.getenv("PORT"));
    } catch (NumberFormatException e) {
      return DEFAULT_PORT;
    }
  }
}
