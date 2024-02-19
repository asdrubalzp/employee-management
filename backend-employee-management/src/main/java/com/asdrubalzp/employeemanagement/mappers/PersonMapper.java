package com.asdrubalzp.employeemanagement.mappers;

import com.asdrubalzp.employeemanagement.dtos.PersonDTO;
import com.asdrubalzp.employeemanagement.models.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {


    public Person mapToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.name());
        person.setLastName(personDTO.lastName());
        person.setEmail(personDTO.email());
        person.setBirthDate(personDTO.birthDate());
        person.setSalary(personDTO.salary());

        return person;
    }
    public PersonDTO mapToPersonDto(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getLastName(), person.getEmail(), person.getBirthDate(), person.getSalary());
    }
}
