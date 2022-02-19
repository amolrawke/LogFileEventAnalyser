package com.assignment.rawkeamo.loganalyser.manager;

import com.assignment.rawkeamo.loganalyser.model.StateMgmBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.rawkeamo.loganalyser.conf.MyAppData;
import com.assignment.rawkeamo.loganalyser.model.ContextBean;
import com.assignment.rawkeamo.loganalyser.model.RaiseEventBean;
import com.assignment.rawkeamo.loganalyser.model.persistence.AlertBean;
import com.assignment.rawkeamo.loganalyser.repository.MyEventRaiseRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class LoggingManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingManager.class);

    @Autowired
    private MyEventRaiseRepository myEventRaiseRepository;

    @Autowired
    private MyAppData myAppData;

    public void EventParser(ContextBean context) {

        Map<String, RaiseEventBean> eventMap = new HashMap<>();

        Map<String, AlertBean> alerts = new HashMap<>();

        LOGGER.info("Parsing the events and persisting the alerts. ");
        try (LineIterator li = FileUtils.lineIterator(new ClassPathResource(context.getLogFilePath()).getFile())) {
            String line = null;
            while (li.hasNext()) {
                RaiseEventBean raiseEventBean;
                try {
                    raiseEventBean = new ObjectMapper().readValue(li.nextLine(), RaiseEventBean.class);
                    LOGGER.trace("{}", raiseEventBean);


                    if (eventMap.containsKey(raiseEventBean.getId())) {
                        RaiseEventBean e1 = eventMap.get(raiseEventBean.getId());
                        long executionTime = getEventExecutionTime(raiseEventBean, e1);


                        AlertBean alertBean = new AlertBean(raiseEventBean, Math.toIntExact(executionTime));


                        if (executionTime > myAppData.getAlertThresholdMs()) {
                            alertBean.setAlert(Boolean.TRUE);
                            LOGGER.trace("!!! Execution time for the raiseEventBean {} is {}ms", raiseEventBean.getId(), executionTime);
                        }


                        alerts.put(raiseEventBean.getId(), alertBean);


                        eventMap.remove(raiseEventBean.getId());
                    } else {
                        eventMap.put(raiseEventBean.getId(), raiseEventBean);
                    }
                } catch (JsonProcessingException e) {
                    LOGGER.error("Unable to parse the raiseEventBean! {}", e.getMessage());
                }


                if (alerts.size() > myAppData.getTableRowsWriteoffCount()) {
                    persistAlerts(alerts.values());
                    alerts = new HashMap<>();
                }
            }
            if (alerts.size() != 0) {
                persistAlerts(alerts.values());
            }
        } catch (IOException e) {
            LOGGER.error("!!! Unable to access the file: {}", e.getMessage());
        }
    }

    private void persistAlerts(Collection<AlertBean> alertBeans) {
        LOGGER.debug("Persisting {} alertBeans...", alertBeans.size());
        myEventRaiseRepository.saveAll(alertBeans);
    }

    private long getEventExecutionTime(RaiseEventBean raiseEventBean1, RaiseEventBean raiseEventBean2) {
        RaiseEventBean endRaiseEventBean = Stream.of(raiseEventBean1, raiseEventBean2).filter(e -> StateMgmBean.FINISHED.equals(e.getStateMgmBean())).findFirst().orElse(null);
        RaiseEventBean startRaiseEventBean = Stream.of(raiseEventBean1, raiseEventBean2).filter(e -> StateMgmBean.STARTED.equals(e.getStateMgmBean())).findFirst().orElse(null);

        return Objects.requireNonNull(endRaiseEventBean).getTimestamp() - Objects.requireNonNull(startRaiseEventBean).getTimestamp();
    }
}
