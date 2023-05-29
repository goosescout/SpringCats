package com.goosescout.spring.service.services;


import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatOwnerDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatOwnerServiceException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CatOwnerService {
    CatOwnerDto createCatOwner(String name, String username, String password, LocalDate birthDate, Collection<Role> roles) throws CatOwnerServiceException;
    CatOwnerDto findCatOwnerById(Long id) throws EntityNotFoundException;
    List<CatOwnerDto> findAllCatOwners();
    List<CatOwnerDto> findCatOwnersByName(String name);
    CatOwnerDto findCatOwnerByUsername(String username) throws EntityNotFoundException;
    List<CatOwnerDto> findCatOwnersByBirthDate(LocalDate birthDate);
    CatOwnerDto updateCatOwner(Long id, String name, String username, String password, LocalDate birthDate, Collection<Role> roles) throws EntityNotFoundException, CatOwnerServiceException;
    void deleteCatOwner(Long id);
}
