package at.ac.fhcampuswien.se.group1.authenticationservice.utility;

import at.ac.fhcampuswien.se.group1.authenticationservice.domain.exception.InvalidJwtTokenException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final IUserDetailsService userDetailsService;

    @Value("${car-rental-app.admin.email:admin@admin.io}")
    private String adminUser;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {

            String jwt = authHeader.substring(7);
            checkJsonWebToken(jwt);
            try {
                String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                log.info("Extracted username from token: {}", username);
                if (username.equals(adminUser)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                                        userDetails.getAuthorities()));
                    }
                }
                else {
                    log.info("Setting security context to ROLE_ORDER");
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(username, "",
                                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ORDER"))));

                    }
                }
            } catch (JWTDecodeException | SignatureVerificationException | TokenExpiredException jwtDecodeException) {
                log.error(jwtDecodeException.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }
        filterChain.doFilter(request, response);
    }

    private void checkJsonWebToken(String jwt) throws InvalidJwtTokenException {
        if (jwt.isBlank()) {
            throw new InvalidJwtTokenException("Invalid JWT Token in Bearer Header");
        }

    }
}
