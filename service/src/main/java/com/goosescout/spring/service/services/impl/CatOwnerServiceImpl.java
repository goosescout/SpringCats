package com.goosescout.spring.service.services.impl;

import com.goosescout.spring.dao.entities.CatOwner;
import com.goosescout.spring.dao.repositories.CatOwnerRepository;
import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatOwnerDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatOwnerServiceException;
import com.goosescout.spring.service.mapping.CatOwnerMapping;
import com.goosescout.spring.service.services.CatOwnerService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatOwnerServiceImpl implements CatOwnerService {
    private final CatOwnerRepository catOwnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CatOwnerServiceImpl(CatOwnerRepository catOwnerRepository, PasswordEncoder passwordEncoder) {
        this.catOwnerRepository = catOwnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CatOwnerDto createCatOwner(@NonNull String name,
                                      @NonNull String username,
                                      @NonNull String password,
                                      @NonNull LocalDate birthDate,
                                      @NonNull Collection<Role> roles) throws CatOwnerServiceException {
        if (birthDate.isAfter(LocalDate.now()))
            throw CatOwnerServiceException.createInvalidBirthDate();
        if (catOwnerRepository.existsByUsername(username))
            throw CatOwnerServiceException.createUsernameAlreadyExists(username);

        CatOwner catOwner = new CatOwner(name,
                username,
                passwordEncoder.encode(password),
                birthDate,
                roles.stream().map(Role::getId).collect(Collectors.toSet()));
        catOwnerRepository.save(catOwner);
        return CatOwnerMapping.asDto(catOwner);
    }

    @Override
    public CatOwnerDto findCatOwnerById(@NonNull Long id) throws EntityNotFoundException {
        return CatOwnerMapping.asDto(catOwnerRepository.findById(id).orElseThrow(() -> EntityNotFoundException.createWithId("Cat Owner", id)));
    }

    @Override
    public List<CatOwnerDto> findAllCatOwners() {
        return catOwnerRepository.findAll().stream().map(CatOwnerMapping::asDto).toList();
    }

    @Override
    public List<CatOwnerDto> findCatOwnersByName(@NonNull String name) {
         return catOwnerRepository.findByName(name).stream().map(CatOwnerMapping::asDto).toList();
    }

    @Override
    public CatOwnerDto findCatOwnerByUsername(@NonNull String username) throws EntityNotFoundException {
        return CatOwnerMapping.asDto(catOwnerRepository.findByUsername(username).orElseThrow(() -> EntityNotFoundException.createWithUsername("Cat Owner", username)));
    }

    @Override
    public List<CatOwnerDto> findCatOwnersByBirthDate(@NonNull LocalDate birthDate) {
        return catOwnerRepository.findByBirthDate(birthDate).stream().map(CatOwnerMapping::asDto).toList();
    }

    @Override
    public CatOwnerDto updateCatOwner(@NonNull Long id,
                                      @NonNull String name,
                                      @NonNull String username,
                                      @NonNull String password,
                                      @NonNull LocalDate birthDate,
                                      @NonNull Collection<Role> roles) throws EntityNotFoundException, CatOwnerServiceException {
        CatOwner catOwner = catOwnerRepository.findById(id).orElseThrow(() -> EntityNotFoundException.createWithId("Cat Owner", id));
        if (birthDate.isAfter(LocalDate.now()))
            throw CatOwnerServiceException.createInvalidBirthDate();
        if (catOwnerRepository.existsByUsername(username) && !catOwner.getUsername().equals(username))
            throw CatOwnerServiceException.createUsernameAlreadyExists(username);
        catOwner.setName(name);
        catOwner.setUsername(username);
        catOwner.setPassword(password);
        catOwner.setBirthDate(birthDate);
        catOwner.setRoles(roles.stream().map(Role::getId).collect(Collectors.toSet()));
        catOwnerRepository.save(catOwner);
        return CatOwnerMapping.asDto(catOwner);
    }

    @Override
    public void deleteCatOwner(Long id) {
        catOwnerRepository.deleteById(id);
    }
}
