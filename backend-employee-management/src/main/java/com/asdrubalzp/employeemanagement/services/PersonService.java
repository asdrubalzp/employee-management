package com.asdrubalzp.employeemanagement.services;
import com.asdrubalzp.employeemanagement.dtos.PersonDTO;
import com.asdrubalzp.employeemanagement.dtos.UpdatePersonDTO;
import com.asdrubalzp.employeemanagement.mappers.PersonMapper;
import com.asdrubalzp.employeemanagement.models.Person;
import com.asdrubalzp.employeemanagement.repositories.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper){
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<PersonDTO> findAll(){
        return personRepository.findAll().stream().map(personMapper::mapToPersonDto).collect(Collectors.toList());
    }

    public ResponseEntity<PersonDTO> findById(Long id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(personMapper.mapToPersonDto(person.get()));

    }

    public ResponseEntity<PersonDTO> save(PersonDTO person) {
        Person savedPerson = personRepository.save(personMapper.mapToPerson(person));
        URI location = URI.create("/api/persons/" + savedPerson.getId());
        return ResponseEntity.created(location).body(personMapper.mapToPersonDto(savedPerson));

    }

    public ResponseEntity<Void> update(Long id, UpdatePersonDTO personDTO) {
        
        Optional<Person> searchedPerson = personRepository.findById(id);
        
        if(searchedPerson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Person person = searchedPerson.get();
        
        if(personDTO.name() != null) {
            person.setName(personDTO.name());
        }
        if(personDTO.lastName() != null) {
            person.setLastName(personDTO.lastName());
        }
        if(personDTO.email() != null) {
            person.setEmail(personDTO.email());
        }
        if(personDTO.birthDate() != null) {
            person.setBirthDate(personDTO.birthDate());
        }
        if(personDTO.salary() != null) {
            person.setSalary(personDTO.salary());
        }
        
        if(personDTO.name() == null && personDTO.lastName() == null && personDTO.email() == null && personDTO.birthDate() == null && personDTO.salary() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        personRepository.save(person);
        return ResponseEntity.noContent().build();
        
    }

    public ResponseEntity<Void> delete(Long id) {
        if(!personRepository.existsById(id)){
           return ResponseEntity.notFound().build();
        }
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
