package com.johnteacher.shoppingcart.security.config;

import com.johnteacher.shoppingcart.security.jwt.AuthTokenFilter;
import com.johnteacher.shoppingcart.security.jwt.JwtAuthEntryPoint;
import com.johnteacher.shoppingcart.security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration // tells spring to process this class at startup, allowing autowired for later
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {
    private final ShopUserDetailsService shopUserDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    // aka security bouncer at every request
    // JWT interceptor: reads request headers, extracts JWT, validates it, sets authentication in Spring context
    private final AuthTokenFilter authTokenFilter;

    private static final List<String> SECURED_URLS = List.of("/api/v1/carts/**", "/api/v1/cartItems/**");

    // the bean part makes a singelton to inject anywhere
    // Note: @Component is a class-level-auto-detection, @Bean is a method-level-manual-registration
    @Bean
    public ModelMapper modelMapper() { // dto and entity converter
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // hashes passwords using BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    // builds central authentication engine, used for login endpoint
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // bridges spring security and database users: handles user logins, spring calls this provider, loads user from DB and checks password hash, returns success/failure
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception{
        var authProvider = new DaoAuthenticationProvider(shopUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    // full security blueprint
    // JWT = stateless API (doesn't store info from client's session or previous requests)
    // CSRF protection is for session-based apps
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // auth error handling
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no HTTP sessions stored on server, every request must include JWT
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated() // URLS  in SECURED_URLS require login
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider()); // register auth provider
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); // add JWT filter into chain (aka, run my JWT filter before username/password auth)
        return http.build();
    }

}
