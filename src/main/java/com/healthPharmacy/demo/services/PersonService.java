package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.enums.UserRole;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements UserDetailsService {


    private PersonRepository personRepository;
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public UserDetails findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    //    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public PersonModel savePerson(String cpf, String name, String phoneNumber, String email, String password, UserRole userRole) {
//        if (personRepository.findByEmail(email) == null || personRepository.findByCpf(cpf) == null) {
//
//            PersonModel personModel = new PersonModel();
//            personModel.setCpf(cpf);
//            personModel.setName(name);
//            personModel.setPhoneNumber(phoneNumber);
//            personModel.setEmail(email);
//
//            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
//
//            personModel.setPassword(encryptedPassword);
//            personModel.setRole(userRole);
//
//            return personModel;
//        }
//        throw new UsernameNotFoundException("Person already exists");
//    }
    public void save(PersonModel personModel) {
        personRepository.save(personModel);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email);
    }
}
