package at.ac.fhcampuswien.se.group1.locationservice.config;

import at.ac.fhcampuswien.se.group1.locationservice.utility.IUserDetailsService;
import at.ac.fhcampuswien.se.group1.locationservice.utility.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Configurable
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter filter;
    private final IUserDetailsService userDetailsService;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                )
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/location/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/location").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            logger.error("An exception occurred at authenticationEntryPoint: {}",
                                    authException.toString());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                        }
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
