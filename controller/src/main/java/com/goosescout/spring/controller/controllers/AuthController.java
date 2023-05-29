package com.goosescout.spring.controller.controllers;

import com.goosescout.spring.controller.models.auth.LoginModel;
import com.goosescout.spring.controller.models.auth.LoginResponse;
import com.goosescout.spring.controller.models.auth.RegisterModel;
import com.goosescout.spring.controller.security.JwtProvider;
import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatOwnerDto;
import com.goosescout.spring.service.exceptions.services.CatOwnerServiceException;
import com.goosescout.spring.service.services.CatOwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private final CatOwnerService catOwnerService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(CatOwnerService catOwnerService,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.catOwnerService = catOwnerService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<CatOwnerDto> register(@Valid @RequestBody RegisterModel registerModel) throws CatOwnerServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            catOwnerService.createCatOwner(
                    registerModel.name(),
                    registerModel.username(),
                    registerModel.password(),
                    registerModel.birthDate(),
                    Set.of(Role.USER)
            )
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginModel loginModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.username(),
                        loginModel.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return new LoginResponse(token);
    }
}
