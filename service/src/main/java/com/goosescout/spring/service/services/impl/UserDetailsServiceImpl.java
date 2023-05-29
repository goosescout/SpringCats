package com.goosescout.spring.service.services.impl;

import com.goosescout.spring.dao.entities.CatOwner;
import com.goosescout.spring.dao.repositories.CatOwnerRepository;
import com.goosescout.spring.service.common.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CatOwnerRepository catOwnerRepository;

    @Autowired
    public UserDetailsServiceImpl(CatOwnerRepository catOwnerRepository) {
        this.catOwnerRepository = catOwnerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CatOwner owner = catOwnerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' not found"));

        Set<GrantedAuthority> authorities = owner.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + Role.fromId(role).name()))
                .collect(Collectors.toSet());

        return new User(owner.getUsername(), owner.getPassword(), authorities);
    }
}
