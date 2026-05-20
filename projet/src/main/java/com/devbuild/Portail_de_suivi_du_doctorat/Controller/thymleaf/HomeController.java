package com.devbuild.Portail_de_suivi_du_doctorat.Controller.thymleaf;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login";
        }
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .findFirst()
                .map(role -> switch (role) {
                    case "ROLE_PERSONNEL_ADMIN" -> "redirect:/admin/dashboard";
                    case "ROLE_DIRECTEUR_THESE" -> "redirect:/directeur/inscriptions";
                    case "ROLE_DOCTORANT" -> "redirect:/doctorant/dashboard";
                    default -> "redirect:/login";
                })
                .orElse("redirect:/login");
    }
}
