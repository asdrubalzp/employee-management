package com.asdrubalzp.employeemanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePersonDTO(
        String name,

        String lastName,

        @Email String email,

        @Past LocalDate birthDate, @PositiveOrZero BigDecimal salary

) {


}
