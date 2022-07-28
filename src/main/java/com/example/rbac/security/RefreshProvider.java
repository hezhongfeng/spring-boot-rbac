package com.example.rbac.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RefreshProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTProvider.class);

	private static String refreshSecret;

	@Value("${refresh.secret}")
	public void setRefreshSecret(String secret) {
		refreshSecret = secret;
	}

	private static int jwtExpirationInMs;

	@Value("${refresh.expire}")
	public void setRefreshExpirationInMs(int expire) {
		jwtExpirationInMs = expire;
	}

	// 根据subject生成token
	public static String generateRefresh(String subject) {

		long currentTimeMillis = System.currentTimeMillis();
		Date expirationDate = new Date(currentTimeMillis + jwtExpirationInMs * 1000);

		return Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512, refreshSecret)
				.setExpiration(expirationDate).compact();
	}

	public static Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token).getBody();


		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

		// 获取用户Id
		Long userId = Long.valueOf(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(userId, null, authorities);
	}

	public static boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			LOGGER.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty");
		}
		return false;
	}
}
