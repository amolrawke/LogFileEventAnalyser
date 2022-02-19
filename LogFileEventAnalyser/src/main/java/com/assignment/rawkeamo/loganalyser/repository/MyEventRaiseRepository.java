package com.assignment.rawkeamo.loganalyser.repository;

import com.assignment.rawkeamo.loganalyser.model.persistence.AlertBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEventRaiseRepository extends CrudRepository<AlertBean, String> {
}
