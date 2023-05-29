package com.goosescout.spring.controller.controllers;

import com.goosescout.spring.controller.exceptions.NotAllowedException;
import com.goosescout.spring.controller.models.cat.AddCatModel;
import com.goosescout.spring.controller.models.cat.CreateCatModel;
import com.goosescout.spring.controller.models.cat.EditCatModel;
import com.goosescout.spring.controller.models.cat.UpdateCatModel;
import com.goosescout.spring.controller.models.friendship.FriendshipModel;
import com.goosescout.spring.service.common.CatColor;
import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatDto;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatServiceException;
import com.goosescout.spring.service.services.CatOwnerService;
import com.goosescout.spring.service.services.CatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cats")
@Validated
public class CatController {
    private final CatService catService;
    private final CatOwnerService catOwnerService;

    @Autowired
    public CatController(CatService catService, CatOwnerService catOwnerService) {
        this.catService = catService;
        this.catOwnerService = catOwnerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CatDto> create(@Valid @RequestBody CreateCatModel catModel) throws CatServiceException, EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            catService.createCat(
                    catModel.name(),
                    catModel.birthDate(),
                    catModel.breed(),
                    catModel.color(),
                    catModel.catOwnerId()
            )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CatDto> add(Principal principal, @Valid @RequestBody AddCatModel catModel) throws CatServiceException, EntityNotFoundException {
        Long userId = getPrincipalId(principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            catService.createCat(
                    catModel.name(),
                    catModel.birthDate(),
                    catModel.breed(),
                    catModel.color(),
                    userId
            )
        );
    }

    @GetMapping("/getById")
    public CatDto getCatById(Principal principal, @RequestParam Long id) throws EntityNotFoundException, NotAllowedException {
        Long userId = getPrincipalId(principal);
        CatDto catDto = catService.findCatById(id);
        if (!hasRole(Role.ADMIN) && !Objects.equals(catDto.catOwnerId(), userId))
            throw NotAllowedException.create("You are not allowed to view this cat");
        return catDto;
    }

    @GetMapping("/getAll")
    public List<CatDto> getCats(Principal principal) throws EntityNotFoundException {
        if (hasRole(Role.ADMIN))
            return catService.findAllCats();
        Long userId = getPrincipalId(principal);
        return catService.findCatsByCatOwnerId(userId);
    }

    @GetMapping("/getByName")
    public List<CatDto> getCatByName(Principal principal, @RequestParam String name) throws EntityNotFoundException {
        if (hasRole(Role.ADMIN))
            return catService.findCatsByName(name);
        Long userId = getPrincipalId(principal);
        return catService.findCatsByNameAndCatOwnerId(name, userId);
    }

    @GetMapping("/getByBirthDate")
    public List<CatDto> getCatByBirthDate(
            Principal principal,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) throws EntityNotFoundException {
        if (hasRole(Role.ADMIN))
            return catService.findCatsByBirthDate(date);
        Long userId = getPrincipalId(principal);
        return catService.findCatsByBirthDateAndCatOwnerId(date, userId);
    }

    @GetMapping("/getByBreed")
    public List<CatDto> getCatByBreed(Principal principal, @RequestParam String breed) throws EntityNotFoundException {
        if (hasRole(Role.ADMIN))
            return catService.findCatsByBreed(breed);
        Long userId = getPrincipalId(principal);
        return catService.findCatsByBreedAndCatOwnerId(breed, userId);
    }

    @GetMapping("/getByColor")
    public List<CatDto> getCatByColor(Principal principal, @RequestParam CatColor color) throws EntityNotFoundException {
        if (hasRole(Role.ADMIN))
            return catService.findCatsByColor(color);
        Long userId = getPrincipalId(principal);
        return catService.findCatsByColorAndCatOwnerId(color, userId);
    }

    @GetMapping("/getByCatOwnerId")
    public List<CatDto> getCatByCatOwnerId(Principal principal, @RequestParam Long catOwnerId) throws EntityNotFoundException, NotAllowedException {
        Long userId = getPrincipalId(principal);
        if (!hasRole(Role.ADMIN) && !Objects.equals(userId, catOwnerId))
            throw NotAllowedException.create("You are not allowed to view this user's cats");
        return catService.findCatsByCatOwnerId(catOwnerId);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<HttpStatus> addFriend(@Valid @RequestBody FriendshipModel friendshipModel) throws EntityNotFoundException {
        catService.addFriend(friendshipModel.catId(), friendshipModel.friendId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/removeFriend")
    public ResponseEntity<HttpStatus> removeFriend(@Valid @RequestBody FriendshipModel friendshipModel) throws EntityNotFoundException {
        catService.removeFriend(friendshipModel.catId(), friendshipModel.friendId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public CatDto update(@Valid @RequestBody UpdateCatModel catModel) throws EntityNotFoundException, CatServiceException {
        return catService.updateCat(
                catModel.id(),
                catModel.name(),
                catModel.birthDate(),
                catModel.breed(),
                catModel.color(),
                catModel.catOwnerId()
        );
    }

    @PostMapping("/edit")
    public CatDto edit(Principal principal, @Valid @RequestBody EditCatModel catModel) throws EntityNotFoundException, CatServiceException, NotAllowedException {
        Long userId = getPrincipalId(principal);
        Long ownerId = catService.findCatById(catModel.id()).catOwnerId();
        if (!hasRole(Role.ADMIN) && !Objects.equals(userId, ownerId))
            throw NotAllowedException.create("You are not allowed to edit this cat");
        return catService.updateCat(
                catModel.id(),
                catModel.name(),
                catModel.birthDate(),
                catModel.breed(),
                catModel.color(),
                ownerId
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> delete(Principal principal, @RequestParam Long id) throws EntityNotFoundException, NotAllowedException {
        Long userId = getPrincipalId(principal);
        if (!hasRole(Role.ADMIN) && !Objects.equals(id, userId))
            throw NotAllowedException.create("You are not allowed to delete this cat");
        catService.deleteCat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private boolean hasRole(Role role) {
        String roleName = "ROLE_" + role.name();
        List<GrantedAuthority> authorities = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().stream().map(GrantedAuthority.class::cast).toList();
        return authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    private Long getPrincipalId(Principal principal) throws EntityNotFoundException {
        return catOwnerService.findCatOwnerByUsername(principal.getName()).id();
    }
}
