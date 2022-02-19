package com.assignment.rawkeamo.loganalyser.repository;

import com.assignment.rawkeamo.loganalyser.model.TypeOfEvents;
import com.assignment.rawkeamo.loganalyser.model.persistence.AlertBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MyEventRaiseRepositoryTest {
    @Autowired
    private MyEventRaiseRepository repository;

    @Test
    public void whenFindingCustomerById_thenCorrect() {
        AlertBean alertBean = new AlertBean();
        alertBean.setId("Bean1");
        alertBean.setDuration(3);
        alertBean.setHost("127.0.0.1");
        alertBean.setType(TypeOfEvents.APPLICATION_LOG);

        repository.save(alertBean);
        assertThat(repository.findById("Bean")).isInstanceOf(Optional.class);
    }

}