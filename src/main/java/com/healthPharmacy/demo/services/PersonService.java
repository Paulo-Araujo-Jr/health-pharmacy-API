package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.infra.exception.UserNotFoundException;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.PersonRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public UserDetails findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
            if (findPersonByEmail(email) != null) {
                return findPersonByEmail(email);
            }
        throw new UserNotFoundException(email);
    }

    public Object getAuthenticatedPerson() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
