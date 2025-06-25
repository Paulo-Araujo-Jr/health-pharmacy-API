package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.employee.EmployeeRequestDTO;
import com.healthPharmacy.demo.dto.customer.CustomerResponseDTO;
import com.healthPharmacy.demo.dto.employee.EmployeeResponseDTO;
import com.healthPharmacy.demo.models.EmployeeModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private CustomerService customerService;

    public EmployeeService(EmployeeRepository employeeRepository,  CustomerService customerService) {
        this.employeeRepository = employeeRepository;
        this.customerService = customerService;
    }

    @Transactional
    public void registerEmployee(EmployeeRequestDTO employeeRequestDTO) {

        PersonModel personModel = new PersonModel(employeeRequestDTO.cpf(), employeeRequestDTO.name(), employeeRequestDTO.phoneNumber(), employeeRequestDTO.email(), employeeRequestDTO.password(), UserRole.EMPLOYEE);

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setResponsibility(employeeRequestDTO.responsibility());
        employeeModel.setPersonModel(personModel);

        employeeRepository.save(employeeModel);
    }

    @Transactional
    public void deleteEmployee(String cpf) {
        EmployeeModel employeeModel = employeeRepository.findByPersonModelCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
        if (!employeeModel.getPersonModel().isActive()) throw new UsernameNotFoundException("Employee not found");

        PersonModel personModel = employeeModel.getPersonModel();
        personModel.setActive(false);
        employeeRepository.save(employeeModel);
    }

    public void saveAdmin(){

        String emailAdmin = "admin@admin.com";

        if (employeeRepository.findByPersonModelEmail(emailAdmin).isEmpty()) {
            PersonModel person = new PersonModel("", "Administrator", "", emailAdmin, "admin123", UserRole.ADMIN);

            EmployeeModel admin = new EmployeeModel();
            admin.setPersonModel(person);
            admin.setResponsibility("Administrator");
            employeeRepository.save(admin);
        }
    }

    public EmployeeResponseDTO getEmployeeByCpf(String cpf) {
        EmployeeModel employeeModel = employeeRepository.findByPersonModelCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return employeeModelToResponseDTO(employeeModel);
    }

    private EmployeeResponseDTO employeeModelToResponseDTO(EmployeeModel employeeModel){
        return new EmployeeResponseDTO(employeeModel.getPersonModel().getCpf(), employeeModel.getPersonModel().getName(), employeeModel.getPersonModel().getPhoneNumber(), employeeModel.getPersonModel().getEmail(), employeeModel.getResponsibility(), employeeModel.getPersonModel().isActive());
    }
}
