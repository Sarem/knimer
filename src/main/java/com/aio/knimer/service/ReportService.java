package com.aio.knimer.service;

import com.aio.knimer.model.*;
import com.aio.knimer.repository.ReportChartRepository;
import com.aio.knimer.repository.ReportRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportService {

  private ReportRepository reportRepository;

  private ReportChartRepository chartRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public  List<ReportChart> getChartTypesName(String reportName){
    var reportChart = QReportChart.reportChart;
    var query = new JPAQuery(entityManager);
    query.from(reportChart).where(reportChart.reportName.eq(reportName)).distinct();
    return query.fetch();
  }

  public List<Report> getReports(String reportTypeCode) {
    var qreport = QReport.report;
    var query = new JPAQuery(entityManager);
    query.from(qreport).where(qreport.reportTypeCode.eq(reportTypeCode)).distinct();
    return query.fetch();
  }

  public List<Report> getReports(String reportTypeCode, LocalDateTime from, LocalDateTime to) {
    var qreport = QReport.report;
    var query = new JPAQuery(entityManager);
    query.from(qreport).where(qreport.reportTypeCode.eq(reportTypeCode))
        .where(qreport.reportDate.between(from, to)).distinct();
    return query.fetch();
  }

  public void insertTestData() {

    List<Report> reports=new ArrayList<>();
    Report report1 = new Report(null, "REPORT_A", LocalDateTime.now().minusDays(3), "{\n" +
        "    \"Android\": 300,\n" +
        "    \"STB\": 183,\n" +
        "    \"Samsung\": 117,\n" +
        "    \"Web\": 43,\n" +
        "    \"IOS\": 17\n" +
        "  }");
    reports.add(report1);
    Report report2 = new Report(null, "REPORT_A", LocalDateTime.now().minusDays(2), "{\n" +
        "    \"Android\": 405,\n" +
        "    \"STB\": 197,\n" +
        "    \"Samsung\": 122,\n" +
        "    \"Web\": 50,\n" +
        "    \"IOS\": 12\n" +
        "  }");
    reports.add(report2);
    Report report3 = new Report(null, "REPORT_A", LocalDateTime.now().minusDays(1), "{\n" +
        "    \"Android\": 356,\n" +
        "    \"STB\": 191,\n" +
        "    \"Samsung\": 126,\n" +
        "    \"Web\": 56,\n" +
        "    \"IOS\": 19\n" +
        "  }");
    reports.add(report3);

    reportRepository.saveAll(reports);
  }
}
