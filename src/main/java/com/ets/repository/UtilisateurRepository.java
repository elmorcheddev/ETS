package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.Role;
import com.ets.model.Utilisateur;

import java.util.Optional;
import java.util.List;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur   findByUsername(String username); 

	boolean existsByRole(Role roles);

	List<Utilisateur> findByRole(Role role);

 
	List<Utilisateur> findByRole_Name(String string);

	boolean existsByUsername(String string);
}
