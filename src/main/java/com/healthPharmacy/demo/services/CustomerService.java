package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.customer.CustomerRequestDTO;
import com.healthPharmacy.demo.dto.customer.CustomerResponseDTO;
import com.healthPharmacy.demo.infra.exception.UserNotFoundException;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void registerCustomer(CustomerRequestDTO customerRequestDTO) {
        PersonModel personModel = new PersonModel(
                customerRequestDTO.cpf(),
                customerRequestDTO.name(),
                customerRequestDTO.phoneNumber(),
                customerRequestDTO.email(),
                customerRequestDTO.password(),
                UserRole.CUSTOMER
        );

        CustomerModel customerModel = new CustomerModel();
        customerModel.setAge(customerRequestDTO.age());
        customerModel.setAddress(customerRequestDTO.address());
        customerModel.setPersonModel(personModel);

        customerRepository.save(customerModel);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        CustomerModel customerModel = customerRepository.findByPersonModelId(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!customerModel.getPersonModel().isActive()) throw new UserNotFoundException("User not found");

        PersonModel personModel = customerModel.getPersonModel();
        personModel.setActive(false);
        customerRepository.save(customerModel);
    }

    public CustomerResponseDTO getCustomerByCpf(String cpf) {
        CustomerModel customerModel = customerRepository.findByPersonModelEmail(cpf).orElseThrow(() -> new UserNotFoundException("User not found"));
        return customerModelToResponseDTO(customerModel);
    }

    private CustomerResponseDTO customerModelToResponseDTO(CustomerModel customerModel){
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(customerModel.getPersonModel().getCpf(), customerModel.getPersonModel().getName(), customerModel.getPersonModel().getEmail(), customerModel.getPersonModel().getPhoneNumber(), customerModel.getAge(), customerModel.getAddress(), customerModel.getPersonModel().isActive());
        return customerResponseDTO;
    }

}
