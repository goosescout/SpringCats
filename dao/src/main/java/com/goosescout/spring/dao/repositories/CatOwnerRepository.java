package com.goosescout.spring.dao.repositories;

import com.goosescout.spring.dao.entities.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    List<CatOwner> findByName(String name);
    Optional<CatOwner> findByUsername(String username);
    boolean existsByUsername(String username);
    List<CatOwner> findByBirthDate(LocalDate birthDate);
}
