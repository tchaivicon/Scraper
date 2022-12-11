package com.chae.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.chae.scraper.properties")
@SpringBootApplication
public class ScraperApplication {

    public static void main(String[] args) {SpringApplication.run(ScraperApplication.class, args);}

}
