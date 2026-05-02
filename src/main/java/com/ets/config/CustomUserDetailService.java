package com.ets.config;

 
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ets.model.Utilisateur;
import com.ets.repository.UtilisateurRepository;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UtilisateurRepository utilisateurRepo;
		
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
			Utilisateur utilisateur= utilisateurRepo.findByUsername(username);
			if(utilisateur == null) {
				throw new UsernameNotFoundException("utilisateur n'exist pas "+username);
			}
			  SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().getName());
	            return new User(utilisateur.getUsername(), utilisateur.getPasswordHash(), Collections.singletonList(authority));
	      
		
		}
}
