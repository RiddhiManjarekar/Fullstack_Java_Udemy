package org.studyeasy.SpringStarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.studyeasy.SpringStarter.security.util.constants.Privileges;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig {
    private static  final String[] WHITELISt={
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/resources/**",
        "/posts/**"
  
    };


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeHttpRequests()
        .requestMatchers(WHITELISt)
        .permitAll()
        .requestMatchers("/profile/**").authenticated()
        .requestMatchers("/update_photo/**").authenticated()
        .requestMatchers("/posts/add/**").authenticated()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
        .requestMatchers("/test/**").hasAuthority(Privileges.ACCESS_ADMIN_PANEL.getPrivilege())
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/",true)
        .failureUrl("/login?error")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .rememberMe().rememberMeParameter("remember-me")
        .and()
        .httpBasic();



        // TO DO: remove these after upgrading the DB from H2 in file DB 
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
