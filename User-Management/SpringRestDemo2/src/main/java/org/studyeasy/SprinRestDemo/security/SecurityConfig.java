package org.studyeasy.SprinRestDemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RSAKey rsaKey;

   @Bean
   public JWKSource<SecurityContext> jwkSource(){
    rsaKey=Jwks.generateRsa();
    JWKSet jwkSet=new JWKSet(rsaKey);
    return (jwkSelector,securityContext)->jwkSelector.select(jwkSet);
   }

   @Bean
   public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }

    // @Bean
    // public InMemoryUserDetailsManager users() {
    //     return new InMemoryUserDetailsManager(
    //             User.withUsername("Riddhi")
    //                     .password("{noop}password")
    //                     .authorities("read")
    //                     .build());
    // }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }


    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.ignoringRequestMatchers("/db-console/**"))
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests()
                //.requestMatchers("/token").permitAll()
                .requestMatchers("/api/v1/auth/token").permitAll()    
                .requestMatchers("/api/v1/auth/users/add").permitAll()
                .requestMatchers("/api/v1/auth/users").hasAuthority("SCOPE_ADMIN")    //instead oF hasRole()--Monolithic apps
                .requestMatchers("/api/v1/auth/users/{user_id}/update-authorities/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers("/api/v1/auth/profile").authenticated()
                .requestMatchers("/api/v1/auth/profile/update-password").authenticated()
                .requestMatchers("/api/v1/auth/profile/delete").authenticated()
                .requestMatchers("/api/v1/albums").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}").authenticated()
                .requestMatchers("/api/v1/albums/add").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}/upload-photos").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}/update").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}/photos/{photo_id}/update").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}/photos/{photo_id}/download-photo").authenticated()
                .requestMatchers("/api/v1/albums/{album_id}/photos/{photo_id}/download-thumbnail").authenticated()
                .requestMatchers("/").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/db-console/**").permitAll()
                .requestMatchers("/test").authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                
                // TODO: remove these after upgrading the DB from H2 infile DB
                http.csrf().disable();
                http.headers().frameOptions().disable();

        return http.build();
    }

}
