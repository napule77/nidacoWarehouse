package com.amalfi.nidaco.service;

import com.amalfi.nidaco.entity.Role;
import com.amalfi.nidaco.entity.User;
import com.amalfi.nidaco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utente non trovato: " + username
                        )
                );


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toSet())
                )
                .build();
    }

}