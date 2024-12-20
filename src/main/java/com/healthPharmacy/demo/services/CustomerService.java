package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.CustomerRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private PersonService personService;
    public CustomerService(CustomerRepository customerRepository, PersonService personService) {
        this.customerRepository = customerRepository;
        this.personService = personService;
    }

    public void registerCustomer(CustomerDTO customerDTO) {
        if (customerRepository.findByEmail(customerDTO.email()) != null || customerRepository.findByCpf(customerDTO.cpf()) != null) {
            throw new RuntimeException("Customer already exists");
        }

        PersonModel personModel = new PersonModel();
        personModel.setCpf(customerDTO.cpf());
        personModel.setName(customerDTO.name());
        personModel.setPhoneNumber(customerDTO.phoneNumber());
        personModel.setEmail(customerDTO.email());

        String encryptedPassword = new BCryptPasswordEncoder().encode(customerDTO.password());

        personModel.setPassword(encryptedPassword);
        personModel.setRole(UserRole.ADMIN);

        personService.savePerson(personModel);
        System.out.println("Customer registered successfully");
        CustomerModel customerModel = new CustomerModel();
        customerModel.setAge(customerDTO.age());
        customerModel.setAddress(customerDTO.address());
        customerModel.setPersonModel(personModel);

        customerRepository.save(customerModel);
        System.out.println("Customer registered successfully");

    }
}
