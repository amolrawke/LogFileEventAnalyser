package com.assignment.rawkeamo.loganalyser.validator;

import com.assignment.rawkeamo.loganalyser.model.ContextBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class LoggingValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingValidator.class);

    public void validateInput(ContextBean context, String... args) {
        LOGGER.info("Validating the input...");

        validateArguments(args);
        validateFilePath(context, args[0]);
    }

    private void validateFilePath(ContextBean context, String logFilePath) {
        LOGGER.info("Log file specified for MyLoggerService: {}", logFilePath);
        context.setLogFilePath(logFilePath);

        try {
            File file = new ClassPathResource(logFilePath).getFile();
            if (!file.exists()) {
                file = new ClassPathResource(logFilePath).getFile();
                if (!file.exists()) {
                    file = new File(logFilePath);
                }
            }

            if (!file.exists())
                throw new FileNotFoundException("Unable to open the file " + logFilePath);
        } catch (IOException e) {
            LOGGER.error("Unable to find the specified file '{}'", logFilePath);
        }
    }

    private void validateArguments(String[] args) {
        LOGGER.info("Validating arguments...");
        if (args.length < 1) {
            throw new IllegalArgumentException("Please specify the filepath to analyse.");
        }
    }
}
