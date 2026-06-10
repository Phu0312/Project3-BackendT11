package com.devon.building.config;


import com.devon.building.security.CustomOAuth2UserService;
import com.devon.building.security.CustomSuccessHandler;
import com.devon.building.service.impl.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {

    CustomOAuth2UserService customOAuth2UserService;
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/api/user/register", "/oauth2/**", "/login/oauth2/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/users/list").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/users").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/buildings/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/buildings/assign").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/buildings/*/staff").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/admin/customers/list", "/admin/customers/edit").hasAnyRole("MANAGER", "STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/customers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/customers", "/api/customers/assignment", "/api/transactions").hasAnyRole("MANAGER", "STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/customers", "/api/transactions").hasAnyRole("MANAGER", "STAFF")
                        .requestMatchers(HttpMethod.GET, "/api/customers/*/staff").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/customers", "/api/transactions/**").hasRole("MANAGER")
                        .requestMatchers("/admin/**").hasAnyRole("STAFF", "MANAGER")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"))
                .formLogin(form -> form
                                .loginPage("/admin/login")
                                .loginProcessingUrl("/j_spring_security_check")
                                .successHandler(myAuthenticationSuccessHandler())
//                        .defaultSuccessUrl("/admin/accountInfo", true)
                                .failureUrl("/admin/login?incorrectAccount")
                                .usernameParameter("userName")
                                .passwordParameter("password")
                                .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/admin/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomSuccessHandler();
    }
}
