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
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.Date;
import java.util.List;


public class JWTProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTProvider.class);

	@Value(value = "${jwt.secret}")
	private static String jwtSecret;

	@Value(value = "${jwt.expire}")
	private static int jwtExpirationInMs;

	// 根据subject生成token
	public static String generateToken(String subject) {

		long currentTimeMillis = System.currentTimeMillis();
		Date currentDate = new Date(currentTimeMillis);
		Date expirationDate = new Date(currentTimeMillis + jwtExpirationInMs * 1000);

		return Jwts.builder().setSubject(subject).setIssuedAt(currentDate).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public static Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.valueOf(claims.getSubject());
	}

	public static Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		// 从jwt获取用户权限列
		// 注意这里不需要从数据库查询，否则会造成性能浪费，只需要在封路成功颁发jwt的时候查询一次就可以了
		// 关于这部分的讨论：https://stackoverflow.com/questions/51507978/is-it-more-efficient-to-store-the-permissions-of-the-user-in-an-jwt-claim-or-to
		List<GrantedAuthority> authorities =
				AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("permissions"));
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
