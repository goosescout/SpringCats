package com.goosescout.spring.controller.controllers;

import com.goosescout.spring.controller.models.owner.CreateCatOwnerModel;
import com.goosescout.spring.controller.models.owner.UpdateCatOwnerModel;
import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatOwnerDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatOwnerServiceException;
import com.goosescout.spring.service.services.CatOwnerService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.constant.Constable;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/owners")
@Validated
public class CatOwnerController {
    private final CatOwnerService catOwnerService;

    @Autowired
    public CatOwnerController(CatOwnerService catOwnerService) {
        this.catOwnerService = catOwnerService;
    }

    @PostConstruct
    public void init() {
        try {
            catOwnerService.createCatOwner(
                    "Michael Gurevich",
                    "admin",
                    "admin",
                    LocalDate.of(2002, 12, 21),
                    Set.of(Role.ADMIN, Role.USER)
            );
        } catch (CatOwnerServiceException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CatOwnerDto> create(@Valid @RequestBody CreateCatOwnerModel catOwnerModel) throws CatOwnerServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                catOwnerService.createCatOwner(
                        catOwnerModel.name(),
                        catOwnerModel.username(),
                        catOwnerModel.password(),
                        catOwnerModel.birthDate(),
                        catOwnerModel.roles()
                )
        );
    }

    @GetMapping("/getById")
    public CatOwnerDto getById(@RequestParam Long id) throws EntityNotFoundException {
        return catOwnerService.findCatOwnerById(id);
    }

    @GetMapping("/getAll")
    public List<CatOwnerDto> getAll() {
        return catOwnerService.findAllCatOwners();
    }

    @GetMapping("/getByName")
    public List<CatOwnerDto> getByName(@RequestParam String name) {
        return catOwnerService.findCatOwnersByName(name);
    }

    @GetMapping("/getByUsername")
    public CatOwnerDto getByUsername(@RequestParam String username) throws EntityNotFoundException {
        return catOwnerService.findCatOwnerByUsername(username);
    }

    @GetMapping("/getByBirthDate")
    public List<CatOwnerDto> getByBirthDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return catOwnerService.findCatOwnersByBirthDate(date);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    // TODO: better typing?
    public ResponseEntity<Object> update(Principal principal, @Valid @RequestBody UpdateCatOwnerModel catOwnerModel) throws EntityNotFoundException, CatOwnerServiceException {
        Long selfId = catOwnerService.findCatOwnerByUsername(principal.getName()).id();
        if (selfId.equals(catOwnerModel.id()) && !catOwnerModel.roles().contains(Role.ADMIN))
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("status", HttpStatus.FORBIDDEN.value(), "error", "You cannot demote yourself from admin role"));
        return ResponseEntity.status(HttpStatus.OK).body(catOwnerService.updateCatOwner(
                catOwnerModel.id(),
                catOwnerModel.name(),
                catOwnerModel.username(),
                catOwnerModel.password(),
                catOwnerModel.birthDate(),
                catOwnerModel.roles()
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<Map<String, Constable>> delete(Principal principal, @RequestParam Long id) throws EntityNotFoundException {
        Long selfId = catOwnerService.findCatOwnerByUsername(principal.getName()).id();
        if (selfId.equals(id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("status", HttpStatus.FORBIDDEN.value(), "error", "You cannot delete yourself"));
        catOwnerService.deleteCatOwner(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
