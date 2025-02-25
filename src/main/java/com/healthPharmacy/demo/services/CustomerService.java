package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void registerCustomer(CustomerDTO customerDTO) {
        PersonModel personModel = new PersonModel(
                customerDTO.cpf(),
                customerDTO.name(),
                customerDTO.phoneNumber(),
                customerDTO.email(),
                customerDTO.password(),
                UserRole.CUSTOMER
        );

        CustomerModel customerModel = new CustomerModel();
        customerModel.setAge(customerDTO.age());
        customerModel.setAddress(customerDTO.address());
        customerModel.setPersonModel(personModel);

        customerRepository.save(customerModel);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        CustomerModel customerModel = customerRepository.findByPersonModelId(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!customerModel.getPersonModel().isActive()) throw new UsernameNotFoundException("User not found");

        PersonModel personModel = customerModel.getPersonModel();
        personModel.setActive(false);
        customerRepository.save(customerModel);
    }

}
