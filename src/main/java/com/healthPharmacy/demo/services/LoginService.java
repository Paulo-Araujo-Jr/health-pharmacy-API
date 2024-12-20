package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.repository.CustomerRepository;
import com.healthPharmacy.demo.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class LoginService implements UserDetailsService {

    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return customer;
        }
        UserDetails employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return employee;
        }
        throw new UsernameNotFoundException("User not found");
    }

}
