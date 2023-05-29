package com.goosescout.spring.service.services.impl;

import com.goosescout.spring.dao.entities.Cat;
import com.goosescout.spring.dao.entities.CatOwner;
import com.goosescout.spring.dao.repositories.CatOwnerRepository;
import com.goosescout.spring.dao.repositories.CatRepository;
import com.goosescout.spring.service.common.CatColor;
import com.goosescout.spring.service.dtos.CatDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatServiceException;
import com.goosescout.spring.service.mapping.CatMapping;
import com.goosescout.spring.service.services.CatService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private final CatOwnerRepository catOwnerRepository;

    @Autowired
    public CatServiceImpl(CatRepository catRepository, CatOwnerRepository catOwnerRepository) {
        this.catRepository = catRepository;
        this.catOwnerRepository = catOwnerRepository;
    }

    @Override
    public CatDto createCat(@NonNull String name, @NonNull LocalDate birthDate, String breed, @NonNull CatColor color, @NonNull Long catOwnerId) throws CatServiceException, EntityNotFoundException {
        if (birthDate.isAfter(LocalDate.now()))
            throw CatServiceException.createInvalidBirthDate();
        Cat cat = new Cat(name, birthDate, breed, CatColor.toColor(color), catOwnerRepository.findById(catOwnerId).orElseThrow(() -> EntityNotFoundException.createWithId("Cat Owner", catOwnerId)));
        catRepository.save(cat);
        return CatMapping.asDto(cat);
    }

    @Override
    public CatDto findCatById(@NonNull Long id) throws EntityNotFoundException {
        return CatMapping.asDto(catRepository.findById(id).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", id)));
    }


    @Override
    public List<CatDto> findAllCats() {
        return catRepository.findAll().stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByName(@NonNull String name) {
        return catRepository.findByName(name).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByBirthDate(@NonNull LocalDate birthDate) {
        return catRepository.findByBirthDate(birthDate).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByBreed(String breed) {
        return catRepository.findByBreed(breed).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByColor(@NonNull CatColor color) {
        return catRepository.findByColor(CatColor.toColor(color)).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByCatOwnerId(@NonNull Long catOwnerId) {
        return catRepository.findByCatOwnerId(catOwnerId).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByNameAndCatOwnerId(@NonNull String name, @NonNull Long catOwnerId) {
        return catRepository.findByNameAndCatOwnerId(name, catOwnerId).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByBirthDateAndCatOwnerId(@NonNull LocalDate birthDate, @NonNull Long catOwnerId) {
        return catRepository.findByBirthDateAndCatOwnerId(birthDate, catOwnerId).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByBreedAndCatOwnerId(@NonNull String breed, @NonNull Long catOwnerId) {
        return catRepository.findByBreedAndCatOwnerId(breed, catOwnerId).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public List<CatDto> findCatsByColorAndCatOwnerId(@NonNull CatColor color, @NonNull Long catOwnerId) {
        return catRepository.findByColorAndCatOwnerId(CatColor.toColor(color), catOwnerId).stream().map(CatMapping::asDto).toList();
    }

    @Override
    public void addFriend(Long catId, Long friendId) throws EntityNotFoundException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", catId));
        Cat friend = catRepository.findById(friendId).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", friendId));
        cat.addFriend(friend);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Override
    public void removeFriend(Long catId, Long friendId) throws EntityNotFoundException {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", catId));
        Cat friend = catRepository.findById(friendId).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", friendId));
        cat.removeFriend(friend);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Override
    public CatDto updateCat(@NonNull Long id, @NonNull String name, @NonNull LocalDate birthDate, String breed, @NonNull CatColor color, @NonNull Long catOwnerId) throws EntityNotFoundException, CatServiceException {
        Cat cat = catRepository.findById(id).orElseThrow(() -> EntityNotFoundException.createWithId("Cat", id));
        if (birthDate.isAfter(LocalDate.now()))
            throw CatServiceException.createInvalidBirthDate();
        cat.setName(name);
        cat.setBirthDate(birthDate);
        cat.setBreed(breed);
        cat.setColor(CatColor.toColor(color));
        CatOwner catOwner = catOwnerRepository.findById(catOwnerId).orElseThrow(() -> EntityNotFoundException.createWithId("CatOwner", catOwnerId));
        catOwnerRepository.save(catOwner);
        return CatMapping.asDto(cat);
    }

    @Override
    public void deleteCat(@NonNull Long id) {
        catRepository.deleteById(id);
    }
}
