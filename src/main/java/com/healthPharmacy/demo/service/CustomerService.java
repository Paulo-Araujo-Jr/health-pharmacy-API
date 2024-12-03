package com.healthPharmacy.demo.service;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.model.CustomerModel;
import com.healthPharmacy.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerCustomer(CustomerDTO customerDTO) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCpf(customerDTO.cpf());
        customerModel.setName(customerDTO.name());
        customerModel.setPhoneNumber(customerDTO.phoneNumber());
        customerModel.setEmail(customerDTO.email());
        customerModel.setPassword(customerDTO.password());
        customerModel.setAge(customerDTO.age());
        customerModel.setAddress(customerDTO.address());
        customerRepository.save(customerModel);
    }
}
