package com.aio.knimer.repository;

import com.aio.knimer.model.Report;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends CrudRepository<Report, Long>
        , QuerydslPredicateExecutor<Report> {

}
