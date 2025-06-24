package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.EmployeeDTO;
import com.healthPharmacy.demo.models.EmployeeModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void registerEmployee(EmployeeDTO employeeDTO) {

        PersonModel personModel = new PersonModel(employeeDTO.cpf(), employeeDTO.name(), employeeDTO.phoneNumber(), employeeDTO.email(), employeeDTO.password(), UserRole.EMPLOYEE);

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setResponsibility(employeeDTO.responsibility());
        employeeModel.setPersonModel(personModel);

        employeeRepository.save(employeeModel);
    }

    public void deleteEmployee(Long id) {
        EmployeeModel employeeModel = employeeRepository.findByPersonModelId(id).orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
        if (!employeeModel.getPersonModel().isActive()) throw new UsernameNotFoundException("Employee not found");

        PersonModel personModel = employeeModel.getPersonModel();
        personModel.setActive(false);
        employeeRepository.save(employeeModel);
    }
}
