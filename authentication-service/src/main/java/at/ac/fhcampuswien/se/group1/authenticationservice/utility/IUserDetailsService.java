package at.ac.fhcampuswien.se.group1.authenticationservice.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class IUserDetailsService implements UserDetailsService {
    
    private final PasswordEncoder passwordEncoder;

    @Value("${car-rental-app.admin.email:admin@admin.io}")
    String adminUser;
    @Value("${car-rental-app.admin.password:admin123}")
    String adminPassword;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ( ! username.equals(adminUser)) {
            throw new UsernameNotFoundException("User with specified username could not be found");
        }
        return new User(
                adminUser,
                passwordEncoder.encode(adminPassword),
                Collections.singletonList(new SimpleGrantedAuthority("AUTHORITY_ADMINISTRATOR")));
    }
}
