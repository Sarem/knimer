package com.aio.knimer.controllers;

import com.aio.knimer.model.ChartType;
import com.aio.knimer.model.Report;
import com.aio.knimer.model.ReportChart;
import com.aio.knimer.model.ReportDTO;
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
@RequestMapping("/v2")
public class ReportController {

  private ReportService reportService;

//  @Operation(summary = "token", description = "", tags = {"token-controller"})
  @GetMapping("/token")
  public String getToken() {
    return JWTUtil.getJWTToken();
  }



  @GetMapping("/report/{code}")
  public ReportDTO getReport
      (@PathVariable String code, @AuthenticationPrincipal KeycloakAuthenticationToken token) {
    Optional<String> optionalCode = token.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority).filter(s -> s.equalsIgnoreCase(code)).findFirst();
    return new ReportDTO(reportService.getReports(optionalCode.get()),reportService.getChartTypesName(code)
            .stream().map(ReportChart::getChartType).collect(Collectors.toList()));

  }

    @GetMapping("/report-period/{code}/{from}/{to}")
    public ReportDTO getReportPeriod
            (@PathVariable String code, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
             @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
             @AuthenticationPrincipal KeycloakAuthenticationToken token) {
        Optional<String> optionalCode = token.getAuthorities().stream() //
                .map(GrantedAuthority::getAuthority).filter(s -> s.equalsIgnoreCase(code)).findFirst();
        return new ReportDTO(reportService.getReports(optionalCode.get(), from, to),reportService.getChartTypesName(code)
                .stream().map(ReportChart::getChartType).collect(Collectors.toList()));
    }

  @GetMapping("/charts/{code}")
  public List<ChartType> getChartTypes
          (@PathVariable String code) {
    return reportService.getChartTypesName(code)
            .stream().map(ReportChart::getChartType).collect(Collectors.toList());
  }


}
