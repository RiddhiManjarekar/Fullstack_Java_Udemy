package org.studyeasy.SpringStarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.studyeasy.SpringStarter.security.util.constants.Privileges;
import org.studyeasy.SpringStarter.security.util.constants.Roles;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig {
    private static  final String[] WHITELISt={
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/css/**",
        "/fonts/**",
        "/images/**",
        "/js/**",
    };


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
        .authorizeRequests()
        .antMatchers(WHITELISt)
        .permitAll()
        .antMatchers("/profile/**").authenticated()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
        .antMatchers("/test/**").hasAuthority(Privileges.ACCESS_ADMIN_PANEL.getPrivilege())
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



        //TODO: remove these after upgrading the DB from H2 in file DB 
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
