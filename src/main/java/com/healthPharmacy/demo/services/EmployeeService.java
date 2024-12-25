package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.EmployeeDTO;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.models.EmployeeModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.EmployeeRepository;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PersonService personService;

    public EmployeeService(EmployeeRepository employeeRepository, PersonService personService) {
        this.employeeRepository = employeeRepository;
        this.personService = personService;
    }

    public void registerEmployee(EmployeeDTO employeeDTO) {

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setResponsibility(employeeDTO.responsibility());
        PersonModel personModel = new PersonModel(employeeDTO.cpf(), employeeDTO.name(), employeeDTO.phoneNumber(), employeeDTO.email(), employeeDTO.password(), UserRole.EMPLOYEE);
        employeeModel.setPersonModel(personModel);

        personService.save(personModel);

        employeeRepository.save(employeeModel);
        System.out.println("Employee registered successfully");
    }

}
