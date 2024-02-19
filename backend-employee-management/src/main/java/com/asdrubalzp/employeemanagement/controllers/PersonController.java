package com.asdrubalzp.employeemanagement.controllers;


import com.asdrubalzp.employeemanagement.dtos.PersonDTO;
import com.asdrubalzp.employeemanagement.dtos.UpdatePersonDTO;
import com.asdrubalzp.employeemanagement.services.PersonService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/persons")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        return personService.findById(id);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO){
        return personService.save(personDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdatePersonDTO personDTO){
        return personService.update(id, personDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return personService.delete(id);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exp) {

        if (exp.getCause() instanceof org.hibernate.exception.ConstraintViolationException cause) {
            if (Objects.requireNonNull(cause.getConstraintName()).contains("person_email_key")) {
                Map<String, String> errors = new HashMap<>();

                errors.put("status", "400");
                errors.put("error", "Bad Request");
                errors.put("message", "El correo ya está en uso");
                errors.put("path", "/api/persons");


                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(Collections.singletonMap("error", "Error de integridad de datos"), HttpStatus.BAD_REQUEST);
    }



}
