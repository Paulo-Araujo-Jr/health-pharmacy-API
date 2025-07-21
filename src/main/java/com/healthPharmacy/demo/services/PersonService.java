package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.infra.exception.UserNotFoundException;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    public UserDetails findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(PersonModel personModel) {
        String encodedPassword = new BCryptPasswordEncoder().encode(personModel.getPassword());
        personModel.setPassword(encodedPassword);
        personRepository.save(personModel);
    }
    @Override
    public UserDetails loadUserByUsername(String email)  {
            if (findPersonByEmail(email) != null) {
                return findPersonByEmail(email);
            }
        throw new UserNotFoundException(email);
    }
    public void delete(PersonModel personModel) {
        if (personRepository.findById(personModel.getId()).isPresent()) {
            personRepository.delete(personModel);
        }
        throw new UserNotFoundException("User not found");
    }
    public Object getAuthenticatedPerson() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
