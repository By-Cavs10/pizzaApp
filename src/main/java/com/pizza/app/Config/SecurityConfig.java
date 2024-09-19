package com.pizza.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Query pour récupérer l'utilisateur par email
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, 1 as enabled FROM utilisateur WHERE email = ?");

        // Query pour récupérer les rôles par email
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT utilisateur.email, role.libelle FROM utilisateur inner join role_utilisateur ON role_utilisateur.UTILISATEUR_id_utilisateur = utilisateur.id_utilisateur INNER JOIN role ON role_utilisateur.ROLE_id_role = role.id_role WHERE utilisateur.email = ?");
        return jdbcUserDetailsManager;
    }

    // Ajouter le PasswordEncoder pour hacher les mots de passe
   /*@Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();

   }*/
    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/create-produit/{id}").hasAnyRole("GERANT", "PIZZAIOLO")
                        .requestMatchers("/create-produit").hasAnyRole("GERANT", "PIZZAIOLO")
                        .requestMatchers("/panier").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/list").authenticated()
                        .requestMatchers("/ajouterProduit").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/choisirLivraison").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/validerCommande").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/commandes").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/updateEtatCommande").hasAnyRole("GERANT", "PIZZAIOLO","LIVREUR")
                        .requestMatchers("/compte").authenticated()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/utilisateurs").hasRole("GERANT")
                        .requestMatchers("/utilisateurs/edit/{id}").hasRole("GERANT")
                        .requestMatchers("/utilisateurs/edit").hasRole("GERANT")
                        .requestMatchers("/utilisateurs/delete/{id}").hasRole("GERANT")
                        .requestMatchers("/css/**", "/images/**", "/vendor/**").permitAll()
                        .requestMatchers("/proccesLogin").permitAll()
                );

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/proccesLogin").permitAll()
        );

        // Configuration du logout
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));
        http.logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout")
                .addLogoutHandler(clearSiteData)
        );

        return http.build();
    }
}
