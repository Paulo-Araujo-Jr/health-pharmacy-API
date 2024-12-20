package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements UserDetailsService {


    private PersonRepository personRepository;
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public void savePerson(PersonModel person) {
        personRepository.save(person);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email);
    }
}
