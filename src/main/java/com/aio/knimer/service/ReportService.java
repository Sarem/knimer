package com.aio.knimer.service;

import com.aio.knimer.model.QReport;
import com.aio.knimer.model.Report;
import com.aio.knimer.repository.ReportRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportService {

    private ReportRepository reportRepository;
//    private ReportJpaRepository reportJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Report> getReports(String reportTypeCode) {
        var qreport = QReport.report;
        var query = new JPAQuery(entityManager);
        query.from(qreport).where(qreport.reportTypeCode.eq(reportTypeCode)).distinct();
        return query.fetch();
    }

    public void insertTestData() {
        Report report = new Report(null, "REPORT_A", LocalDate.now(), "{}");
        reportRepository.save(report);
    }
}
