package com.aio.knimer.controllers;

import com.aio.knimer.model.Report;
import com.aio.knimer.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TokenController {

  private ReportService reportService;

//  @Operation(summary = "token", description = "", tags = {"token-controller"})
  @GetMapping("/token")
  public String getToken() {
    return JWTUtil.getJWTToken();
  }

  @PreAuthorize("hasAuthority('REPORT_B')")
  @GetMapping("/custom-b")
  public String helloB() {
    return "hello B";
  }

  @PreAuthorize("hasAuthority('REPORT_A')")
  @GetMapping("/custom-a")
  public String helloA() {
    return "hello A";
  }

  @GetMapping("/test")
  public void test() {
    reportService.insertTestData();
  }

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

  @GetMapping("/report-period/{code}/{from}/{to}")
  public List<String> getReportPeriod
      (@PathVariable String code, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
       @AuthenticationPrincipal KeycloakAuthenticationToken token) {
    Optional<String> optionalCode = token.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority).filter(s -> s.equalsIgnoreCase(code)).findFirst();
    return reportService.getReports(optionalCode.get(), from, to)
        .stream().map(Report::getJsonData).collect(Collectors.toList());
  }
}
