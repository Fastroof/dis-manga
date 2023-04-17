package com.fastroof.security.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fastroof.security.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

/**
 * The JwtUtils Class.
 */
@Component
public class JwtUtils {
	
	/** The logger Constant. */
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	/** The jwt secret. */
	@Value("${apricotka.app.jwtSecret}")
	private String jwtSecret;

	/** The jwt expiration ms. */
	@Value("${apricotka.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	/**
	 * Generate jwt token.
	 *
	 * @param authentication the authentication
	 * @return the string
	 */
	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * Gets the username from jwt token.
	 *
	 * @param token the token
	 * @return the username from jwt token
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Validate jwt token.
	 *
	 * @param authToken the auth token
	 * @return true, if successful
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
