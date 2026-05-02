package com.ets.Utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ets.model.Role;
import com.ets.model.Utilisateur;
import com.ets.repository.RoleRepository;
import com.ets.repository.UtilisateurRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminInitializer(
            UtilisateurRepository utilisateurRepo,
            RoleRepository roleRepo,
            BCryptPasswordEncoder passwordEncoder) {

        this.utilisateurRepo = utilisateurRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // 1. Create ADMIN role if not exists
        Role adminRole = roleRepo.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepo.save(adminRole);
            System.out.println("ROLE ADMIN created");
        }

        // 2. Check user by username
        String username = "SCATEC";

        boolean userExists = utilisateurRepo.existsByUsername(username);

        if (!userExists) {
            Utilisateur admin = new Utilisateur();
            admin.setFirstName("SCATEC");
            admin.setLastName("SCATEC");
            admin.setUsername(username);
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);

            utilisateurRepo.save(admin);

            System.out.println("ADMIN user created: " + username + " / admin123");
        }
    }
}