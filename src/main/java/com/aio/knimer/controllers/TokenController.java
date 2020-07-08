package com.aio.knimer.controllers;

import com.aio.knimer.model.ChartType;
import com.aio.knimer.model.Report;
import com.aio.knimer.model.ReportChart;
import com.aio.knimer.service.ReportService;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TokenController {

  private ReportService reportService;

//  @Operation(summary = "token", description = "", tags = {"token-controller"})
  @GetMapping("/token")
  public String getToken() {
    return JWTUtil.getJWTToken();
  }

  @Deprecated//test method
  @PreAuthorize("hasAuthority('REPORT_B')")
  @GetMapping("/custom-b")
  public String helloB() {
    return "hello B";
  }

  @Deprecated//test method
  @PreAuthorize("hasAuthority('REPORT_A')")
  @GetMapping("/custom-a")
  public String helloA() {
    return "hello A";
  }

  @Deprecated//test method
  @GetMapping("/init-test-data")
  public void initTestData() {
    reportService.insertTestData();
  }

  @Deprecated//move to report controller
  @GetMapping("/report/{code}")
  public List<Report> getReport
      (@PathVariable String code, @AuthenticationPrincipal KeycloakAuthenticationToken token) {
    Optional<String> optionalCode = token.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority).filter(s -> s.equalsIgnoreCase(code)).findFirst();
    return reportService.getReports(optionalCode.get());
//        .stream().map(Report::getJsonData).collect(Collectors.toList());

//    return reportService.getReports(code)
//        .stream().map(Report::getJsonData).collect(Collectors.toList());
  }

  @GetMapping("/authority")
  public List<String> getAuthority
          (@AuthenticationPrincipal KeycloakAuthenticationToken token) {
    return token.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).filter(s -> !(s.equals("offline_access")||s.equals("uma_authorization"))).collect(Collectors.toList());
  }



  @Deprecated//move to report controller
  @GetMapping("/report-period/{code}/{from}/{to}")
  public List<String> getReportPeriod
      (@PathVariable String code, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
       @AuthenticationPrincipal KeycloakAuthenticationToken token) {
    Optional<String> optionalCode = token.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority).filter(s -> s.equalsIgnoreCase(code)).findFirst();
    return reportService.getReports(optionalCode.get(), from, to)
        .stream().map(Report::getJsonData).collect(Collectors.toList());
  }

}
