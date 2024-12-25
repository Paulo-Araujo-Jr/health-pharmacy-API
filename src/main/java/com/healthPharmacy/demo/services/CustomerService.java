package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonService personService;

    @Transactional
    public void registerCustomer(CustomerDTO customerDTO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setAge(customerDTO.age());
        customerModel.setAddress(customerDTO.address());
        PersonModel personModel = new PersonModel(customerDTO.cpf(), customerDTO.name(), customerDTO.phoneNumber(), customerDTO.email(), customerDTO.password(), UserRole.CUSTOMER);
        customerModel.setPersonModel(personModel);

        personService.save(customerModel.getPersonModel());

        customerRepository.save(customerModel);
        System.out.println("Customer registered successfully");

    }
}
