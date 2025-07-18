package com.harry.identity.entity;

import com.harry.identity.validator.DobConstraint;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;
    String password;
    @Column(name = "avatar", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String avatar;
    @Column(name = "email", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;
    @Column(name = "firstname", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String firstName;
    @Column(name = "lastname", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String lastName;
    @Column(name = "dob", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    LocalDate dob;

    @Column(name = "city", columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}