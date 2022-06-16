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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTProvider.class);

	private static String jwtSecret;

	@Value("${jwt.secret}")
	public void setJwtSecret(String secret) {
		jwtSecret = secret;
	}

	private static int jwtExpirationInMs;

	@Value("${jwt.expire}")
	public void setJwtExpirationInMs(int expire) {
		jwtExpirationInMs = expire;
	}

	// 根据subject生成token
	public static String generateToken(String subject, List<String> permissions) {

		long currentTimeMillis = System.currentTimeMillis();
		Date expirationDate = new Date(currentTimeMillis + jwtExpirationInMs * 1000);

		return Jwts.builder().setSubject(subject).claim("permissions", permissions)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).setExpiration(expirationDate).compact();
	}

	public static Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		// 从jwt获取用户权限列
		// 注意这里不需要从数据库查询，否则会造成性能浪费，只需要在封路成功颁发jwt的时候查询一次就可以了
		// 关于这部分的讨论：https://stackoverflow.com/questions/51507978/is-it-more-efficient-to-store-the-permissions-of-the-user-in-an-jwt-claim-or-to
		// List<GrantedAuthority> authorities =
		// AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("permissions"));


		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get("permissions").toString().split(","))
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		// List<String> permissions = (List<String>) claims.get("permissions");
		// 获取用户Id
		Long userId = Long.valueOf(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(userId, null, authorities);
	}

	public static boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
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
