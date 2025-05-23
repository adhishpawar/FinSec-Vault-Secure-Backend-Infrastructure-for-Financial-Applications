    package com.adhish.FinSec.CoreSecurity.config;


    import com.adhish.FinSec.CoreSecurity.filter.JWTAuthenticationFilter;
    import com.adhish.FinSec.CoreSecurity.service.MyUserDetailsService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        private JWTAuthenticationFilter jwtFilter;

        @Autowired
        private MyUserDetailsService userDetailsService;

        @Bean
        public DaoAuthenticationProvider authProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }
            @Bean
            public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
            }

            @Bean
            public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12); // Strength can be tuned
            }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            // Allow auth endpoints to everyone
                            .requestMatchers("/auth/**", "/api/user/**").permitAll()

                            // Restrict /audit to MANAGER and TELLER
                            .requestMatchers("/audit/**").hasAnyRole("MANAGER", "TELLER")

                            // Allow only CUSTOMERS to do transaction operations
//                            .requestMatchers("/transaction/**").hasRole("")

                            .requestMatchers("/transaction/approve/**").hasAnyRole("MANAGER", "TELLER","CUSTOMER")

                            // Everything else requires authentication
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authProvider())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }


    }
