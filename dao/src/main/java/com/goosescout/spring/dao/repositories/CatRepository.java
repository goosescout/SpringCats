package com.goosescout.spring.dao.repositories;

import com.goosescout.spring.dao.common.Color;
import com.goosescout.spring.dao.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByName(String name);
    List<Cat> findByBirthDate(LocalDate birthDate);
    List<Cat> findByBreed(String breed);
    List<Cat> findByColor(Color color);
    @Query("from Cat where owner.id = ?1")
    List<Cat> findByCatOwnerId(Long catOwnerId);
    @Query("from Cat where name = ?1 and owner.id = ?2")
    List<Cat> findByNameAndCatOwnerId(String name, Long catOwnerId);
    @Query("from Cat where birthDate = ?1 and owner.id = ?2")
    List<Cat> findByBirthDateAndCatOwnerId(LocalDate birthDate, Long catOwnerId);
    @Query("from Cat where breed = ?1 and owner.id = ?2")
    List<Cat> findByBreedAndCatOwnerId(String breed, Long catOwnerId);
    @Query("from Cat where color = ?1 and owner.id = ?2")
    List<Cat> findByColorAndCatOwnerId(Color color, Long catOwnerId);
}
