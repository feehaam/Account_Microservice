package com.prep.account.security;

import com.prep.account.utilities.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://obla.netlify.app/");
        config.addAllowedOrigin("https://obla.netlify.app/");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->{
                    auth
                            .requestMatchers(HttpMethod.POST, "/access/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/access/**").permitAll()

                            .requestMatchers(HttpMethod.GET, "/account/**").authenticated()
                            .requestMatchers(HttpMethod.POST, "/account/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/account/**").authenticated()

                            .requestMatchers(HttpMethod.PUT, "/status/toggle-two-factor/{status}").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/status/toggle-deactivation/{status}").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/status/toggle-lockout/{userId}/{status}").hasRole("INTERNAL")
                            .requestMatchers(HttpMethod.PUT, "/status/toggle-enabling/{userId}/{status}").hasRole("INTERNAL")
                            .requestMatchers(HttpMethod.PUT, "/status/suspend**").hasRole("INTERNAL")
                            .requestMatchers(HttpMethod.PUT, "/status/add-ban-hour**").hasRole("INTERNAL")

                            .requestMatchers(HttpMethod.PUT, "/recovery/change-password").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/recovery/reset-password").authenticated()

                            .requestMatchers(HttpMethod.PUT, "/role/assign-role/{userId}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/role/get-roles/{userId}").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/role/remove-role/{userId}").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
