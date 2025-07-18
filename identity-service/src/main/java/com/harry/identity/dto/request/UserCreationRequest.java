package com.harry.identity.dto.request;

import com.harry.identity.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_REQUIRED")
    String email;
    String firstName;
    String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}
//String id;
//@Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//String username;
//String password;
//@Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//String email;
//@Column(name = "firstname", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//String firstName;
//@Column(name = "lastname", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//String lastName;
//@Column(name = "dob", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//LocalDate dob;
//
//@Column(name = "city", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
//String city;
//
//@ManyToOne
//Set<Role> roles;