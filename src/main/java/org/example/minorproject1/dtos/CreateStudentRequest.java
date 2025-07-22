package org.example.minorproject1.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.minorproject1.models.Department;
import org.example.minorproject1.models.Student;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String rollNumber;

    @NotNull
    private Department department;

    @NotBlank
    @Email
    private String email;

    private String address;


    public Student convertToStudent(){

        return Student.builder()
                .name(this.name)
                .rollNumber(this.rollNumber)
                .department(this.department)
                .email(this.email)
                .address(this.address).build();
    }
}
