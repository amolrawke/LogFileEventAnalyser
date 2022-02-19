package com.assignment.rawkeamo.loganalyser.service;

import com.assignment.rawkeamo.loganalyser.conf.MyAppData;
import com.assignment.rawkeamo.loganalyser.manager.LoggingManager;
import com.assignment.rawkeamo.loganalyser.model.ContextBean;
import com.assignment.rawkeamo.loganalyser.validator.LoggingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyLoggerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggerService.class);

    @Autowired
    private LoggingValidator validator;

    @Autowired
    private LoggingManager manager;

    @Autowired
    private MyAppData myAppData;

    public void execute(String... args) {
        ContextBean context = ContextBean.getInstance();
        validator.validateInput(context, args);
        manager.EventParser(context);
    }

}
