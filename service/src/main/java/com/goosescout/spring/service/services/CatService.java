package com.goosescout.spring.service.services;

import com.goosescout.spring.service.common.CatColor;
import com.goosescout.spring.service.dtos.CatDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatServiceException;

import java.time.LocalDate;
import java.util.List;

public interface CatService {
    CatDto createCat(String name, LocalDate birthDate, String breed, CatColor color, Long catOwnerId) throws CatServiceException, EntityNotFoundException;
    CatDto findCatById(Long id) throws EntityNotFoundException;
    List<CatDto> findAllCats();
    List<CatDto> findCatsByName(String name);
    List<CatDto> findCatsByBirthDate(LocalDate birthDate);
    List<CatDto> findCatsByBreed(String breed);
    List<CatDto> findCatsByColor(CatColor color);
    List<CatDto> findCatsByCatOwnerId(Long catOwnerId);
    List<CatDto> findCatsByNameAndCatOwnerId(String name, Long catOwnerId);
    List<CatDto> findCatsByBirthDateAndCatOwnerId(LocalDate birthDate, Long catOwnerId);
    List<CatDto> findCatsByBreedAndCatOwnerId(String breed, Long catOwnerId);
    List<CatDto> findCatsByColorAndCatOwnerId(CatColor color, Long catOwnerId);
    void addFriend(Long catId, Long friendId) throws EntityNotFoundException;
    void removeFriend(Long catId, Long friendId) throws EntityNotFoundException;
    CatDto updateCat(Long id, String name, LocalDate birthDate, String breed, CatColor color, Long catOwnerId) throws EntityNotFoundException, CatServiceException;
    void deleteCat(Long id);
}
