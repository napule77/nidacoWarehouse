package com.amalfi.nidaco.config;

import com.amalfi.nidaco.entity.User;
import com.amalfi.nidaco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("CERCO UTENTE: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Utente non trovato")
                );

        System.out.println("PASSWORD DB: " + user.getPassword());

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(r -> r.getName().replace("ROLE_", ""))
                                .toArray(String[]::new)
                )
                .disabled(!user.isEnabled())
                .build();
    }
}