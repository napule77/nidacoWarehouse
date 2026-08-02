package com.amalfi.nidaco.config;

import com.amalfi.nidaco.entity.Role;
import com.amalfi.nidaco.entity.User;
import com.amalfi.nidaco.repository.UserRepository;
import com.amalfi.nidaco.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) {


        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() ->
                        roleRepository.save(
                                Role.builder()
                                        .name("ROLE_ADMIN")
                                        .build()
                        )
                );


        if (userRepository.findByUsername("admin").isEmpty()) {


            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .enabled(true)
                    .build();


            admin.getRoles().add(adminRole);


            userRepository.save(admin);


            System.out.println(
                    ">>> Creato utente admin / admin123"
            );
        }

    }
}