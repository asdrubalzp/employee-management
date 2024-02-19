package com.asdrubalzp.employeemanagement.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonDTO(

        Long id,

        @NotEmpty String name,

        @NotEmpty String lastName,

        @NotEmpty @Email String email,

        @NotNull @Past LocalDate birthDate,

        @NotNull @PositiveOrZero BigDecimal salary

) {

}
