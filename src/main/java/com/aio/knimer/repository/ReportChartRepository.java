package com.aio.knimer.repository;

import com.aio.knimer.model.Report;
import com.aio.knimer.model.ReportChart;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportChartRepository extends CrudRepository<ReportChart, Long>
        , QuerydslPredicateExecutor<Report> {

}
