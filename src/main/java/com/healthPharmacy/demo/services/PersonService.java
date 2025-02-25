package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (findPersonByEmail(email) != null) {
            return findPersonByEmail(email);
        }
        throw new UsernameNotFoundException(email);
    }
    public void delete(PersonModel personModel) {
        if (personRepository.findById(personModel.getId()).isPresent()) {
            personRepository.delete(personModel);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
