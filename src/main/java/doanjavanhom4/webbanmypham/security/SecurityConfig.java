package doanjavanhom4.webbanmypham.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    public UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/css/**", "/js/**", "/", "/register", "/error", "/images/**", "/scss/**", "/fonts/**", "/vendor/**","/assets")
                        .permitAll()
                        .requestMatchers("/admin/product/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/category/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/customer/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/comment/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/home/**").hasAnyAuthority("ADMIN")
                        .anyRequest().permitAll()
                ) .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/default")
                        .permitAll()
                )

                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .build();

    }

}
