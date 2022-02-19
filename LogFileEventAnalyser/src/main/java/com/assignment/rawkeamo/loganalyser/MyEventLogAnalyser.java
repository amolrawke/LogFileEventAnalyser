package com.assignment.rawkeamo.loganalyser;

import com.assignment.rawkeamo.loganalyser.service.MyLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class MyEventLogAnalyser implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyEventLogAnalyser.class);

    @Autowired
    private MyLoggerService service;

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(MyEventLogAnalyser.class);
        //app.setBanner(new ImageBanner(new ClassPathResource("logo.png")));
        app.run(args);
    }

    @Override
    public void run(String... args) {
        Instant starttime = Instant.now();
        service.execute(args);
        Instant endtime = Instant.now();
        LOGGER.info("Total time: {}ms", Duration.between(starttime, endtime).toMillis());
    }
}
