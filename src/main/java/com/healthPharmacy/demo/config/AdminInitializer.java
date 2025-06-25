package com.healthPharmacy.demo.config;

import com.healthPharmacy.demo.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private EmployeeService employeeService;


    public AdminInitializer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) {
        employeeService.saveAdmin();
    }
}
