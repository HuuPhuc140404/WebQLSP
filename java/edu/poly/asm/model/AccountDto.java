package edu.poly.asm.model;

import jakarta.persistence.Column;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Column(length = 30)
    private String username;
    @Column(length = 100, nullable = false)
    private String password;

    private Boolean isEdit = false;
}
