package com.devbuild.Portail_de_suivi_du_doctorat.Services;

import com.devbuild.Portail_de_suivi_du_doctorat.entities.Utilisateur;
import com.devbuild.Portail_de_suivi_du_doctorat.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Tentative de connexion avec email inconnu : {}", email);
                    return new UsernameNotFoundException(
                            "Aucun utilisateur trouvé avec l'email : " + email);
                });

        if (!utilisateur.isActif()) {
            log.warn("Tentative de connexion sur un compte désactivé : {}", email);
            throw new UsernameNotFoundException(
                    "Compte désactivé : " + email);
        }

        String role = "ROLE_" + utilisateur.getRole().name();

        log.debug("Authentification réussie pour {} avec rôle {}", email, role);

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .authorities(List.of(new SimpleGrantedAuthority(role)))
                .build();
    }
}
