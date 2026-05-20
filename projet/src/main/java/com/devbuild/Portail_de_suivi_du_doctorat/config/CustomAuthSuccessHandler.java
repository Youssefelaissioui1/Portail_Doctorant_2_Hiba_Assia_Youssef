package com.devbuild.Portail_de_suivi_du_doctorat.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Redirige chaque utilisateur vers son dashboard après connexion.
 */
@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            switch (authority.getAuthority()) {
                case "ROLE_PERSONNEL_ADMIN"  -> { return "/admin/dashboard"; }
                case "ROLE_DIRECTEUR_THESE"  -> { return "/directeur/inscriptions"; }
                case "ROLE_DOCTORANT"        -> { return "/doctorant/dashboard"; }
            }
        }
        return "/login?error";
    }
}
