package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.EmployeeDTO;
import com.healthPharmacy.demo.models.EmployeeModel;
import com.healthPharmacy.demo.models.enums.Role;
import com.healthPharmacy.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void registerEmployee(EmployeeDTO employeeDTO) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setCpf(employeeDTO.cpf());
        employeeModel.setName(employeeDTO.name());
        employeeModel.setPhoneNumber(employeeDTO.phoneNumber());
        employeeModel.setEmail(employeeDTO.email());
        employeeModel.setPassword(employeeDTO.password());
        employeeModel.setResponsibility(employeeDTO.responsibility());
        employeeModel.setRole(Role.EMPLOYEE);
        employeeRepository.save(employeeModel);
    }
}
