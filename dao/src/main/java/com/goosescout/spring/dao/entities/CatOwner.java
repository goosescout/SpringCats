package com.goosescout.spring.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
public class CatOwner {
    @Id
    @GeneratedValue(generator = "increment")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Cat> cats;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> roles;

    public CatOwner(String name, String username, String password, LocalDate birthDate, Collection<Integer> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.roles = new HashSet<>(roles);
    }
}
