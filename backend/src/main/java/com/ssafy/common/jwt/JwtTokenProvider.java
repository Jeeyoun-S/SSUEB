package com.ssafy.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
// JWT Token의 생성 및 유효성 검증 등을 담당하는 클래스 
public class JwtTokenProvider implements InitializingBean {
	
	public static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";
	private final String secret;
	
	private Key key;
	
	// Token 유효시간 
	private long tokenValidTime; 
	
	public JwtTokenProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration}") long expiration) {
		this.secret = secret; 
		this.tokenValidTime = expiration; 
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	/**
	 * 객체의 권한정보를 이용하여 Token 생성
	 * @param authentication
	 * @return String
	 */
	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		// Token 만료시간 설정
		long now = (new Date()).getTime(); 
		Date validity = new Date(now + this.tokenValidTime);
		
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS384)
				.setExpiration(validity)
				.compact(); 
	}
	
	/**
	 * Token에 담겨있는 정보(권한)를 사용하여 Authentication 객체 반환
	 * @param String
	 * @return UsernamePasswordAuthenticationToken
	 */
	public Authentication getAuthentication(String token) {
		// i) token을 사용하여 claims 생성
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		// ii) claims에 있는 권한 추출
		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()); 
		
		// iii) 권한 정보를 사용하여 user 객체 생성
		User principal = new User(claims.getSubject(), "", authorities); 
		
		// iv) user 객체, 토큰, 권한정보를 사용하여 최종적으로 Authentication 객체를 반환
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	/**
	 * Token의 유효성 검증 수행
	 * @param String
	 * @return boolean
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true; 
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT 토큰이 잘못되었습니다.");
		}
		
		return false; 
	}

}
