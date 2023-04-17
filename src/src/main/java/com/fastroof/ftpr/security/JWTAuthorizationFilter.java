package com.fastroof.ftpr.security;

import com.fastroof.ftpr.service.auth.Auth;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * The JWTAuthorizationFilter Class.
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /** The authorization header. */
    private final String HEADER = "Authorization";
    
    /** The authorization prefix. */
    private final String PREFIX = "Bearer ";

    /** The auth service. */
    private final Auth authService;

    /**
     * Instantiates a new JWT authorization filter.
     *
     * @param authService the auth service
     */
    @Autowired
    public JWTAuthorizationFilter(Auth authService) {
        this.authService = authService;
    }

    /**
     * Filter HttpServletRequest.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws ServletException, IOException {
        try {
            if (hasValidAuthenticationHeader(request)) {
                UserDetailsImpl userDetails = validateToken(request);
                if (userDetails != null) {
                    setUpSpringAuthentication(userDetails);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * Validate token with Auth Service.
     *
     * @param request the request
     * @return the user details impl
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private UserDetailsImpl validateToken(HttpServletRequest request) throws IOException {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return authService.validateToken(jwtToken);
    }

    /**
     * Sets up the authentication in SecurityContext.
     *
     * @param userDetails the user details
     */
    private void setUpSpringAuthentication(UserDetailsImpl userDetails) {
        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * Check if request has valid authentication header.
     *
     * @param request the request
     * @return true, if valid
     */
    private boolean hasValidAuthenticationHeader(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

}
