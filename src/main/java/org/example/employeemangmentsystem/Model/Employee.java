package org.example.employeemangmentsystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotNull(message = "id must be not empty")
    private int id;
    @NotNull(message = "name must be not empty")
    private String name;
    @NotNull(message = "email must be not empty")
    private String email;
    @NotNull(message = "phoneNumber must not be empty")
    private String phoneNumber;
    @NotNull(message = "age must be not empty")
    private int age;
    @NotNull(message = "position must be not empty")
    private String position;

    @NotNull(message = "onLeave must be not empty")
    private boolean onLeave;

    @NotNull(message = "onLeave must be not empty")
    private LocalDate hireDate;
    @NotNull(message = "annualLeave must be not empty")
    private int annualLeave;

}
