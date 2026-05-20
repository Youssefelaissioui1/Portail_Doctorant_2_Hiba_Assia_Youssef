package com.devbuild.Portail_de_suivi_du_doctorat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    public SecurityConfig(CustomAuthSuccessHandler customAuthSuccessHandler) {
        this.customAuthSuccessHandler = customAuthSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Keeping it disabled as requested by previous configuration, but will fix templates

                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )

                .authorizeHttpRequests(auth -> auth

                        // ── Routes PUBLIQUES ──────────────────────────────────────────
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/auth/**",
                                "/error",
                                "/h2-console/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                        ).permitAll()

                        // ── Routes ADMIN ──────────────────────────────────────────────
                        .requestMatchers("/admin/**").hasRole("PERSONNEL_ADMIN")

                        // ── Routes DIRECTEUR ──────────────────────────────────────────
                        .requestMatchers("/directeur/**").hasRole("DIRECTEUR_THESE")

                        // ── Routes DOCTORANT (partagées avec directeur/admin) ───────────
                        .requestMatchers(
                                "/doctorant/recherche",
                                "/doctorant/profil/*"
                        ).hasAnyRole("DOCTORANT", "DIRECTEUR_THESE", "PERSONNEL_ADMIN")

                        // ── Routes DOCTORANT ──────────────────────────────────────────
                        .requestMatchers("/doctorant/**").hasRole("DOCTORANT")
                        .requestMatchers("/inscriptions/**")
                            .hasAnyRole("DOCTORANT", "DIRECTEUR_THESE", "PERSONNEL_ADMIN")
                        .requestMatchers("/publications/**")
                            .hasAnyRole("DOCTORANT", "DIRECTEUR_THESE", "PERSONNEL_ADMIN")
                        .requestMatchers("/formations/**")
                            .hasAnyRole("DOCTORANT", "PERSONNEL_ADMIN")
                        .requestMatchers("/soutenance/**").hasRole("DOCTORANT")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthSuccessHandler)
                        .usernameParameter("email")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}