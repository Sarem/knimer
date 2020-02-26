package com.aio.knimer.controllers;

import com.aio.knimer.model.Report;
import com.aio.knimer.service.ReportService;
import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TokenController {

    private ReportService reportService;

    @RequestMapping("/token")
    public String getToken(){
        return JWTUtil.getJWTToken();
    }

    @PreAuthorize("hasAuthority('REPORT_B')")
    @RequestMapping("/custom-b")
    public String helloB(){
        return "hello B";
    }

    @PreAuthorize("hasAuthority('REPORT_A')")
    @RequestMapping("/custom-a")
    public String helloA(){
        return "hello A";
    }

    @RequestMapping("/test")
    public void test(){
        reportService.insertTestData();
    }

    @RequestMapping("/report/{code}")
    public List<String> getReport
            (@PathVariable String code,@AuthenticationPrincipal KeycloakAuthenticationToken token)
    {
        System.out.println(code);
        System.out.println(token);
        Optional<String> optionalCode = token.getAuthorities().stream() //
                .map(GrantedAuthority::getAuthority).filter(s -> s.equals(code)).findFirst();
        return reportService.getReports(optionalCode.get()).stream().map(Report::getJsonData).collect(Collectors.toList());
    }
}
