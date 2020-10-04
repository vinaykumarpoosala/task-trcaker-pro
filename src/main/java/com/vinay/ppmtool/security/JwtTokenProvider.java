package com.vinay.ppmtool.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.vinay.ppmtool.security.SecurityConstants.*;


import com.vinay.ppmtool.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JwtTokenProvider {

	/*
	 * generate token
	 * 
	 * validate token
	 * 
	 * user id from token
	 */
	
	public String generateToken(Authentication authentication) {
		
		User user =  (User) authentication.getPrincipal();
		
		Date now = new Date(System.currentTimeMillis());
		Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String , Object> claims = new HashMap<String, Object>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullName", user.getFullName());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expirationTime)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		}
		catch(SignatureException ex) {
			System.out.println("Inavalid JWT Signature");
		}
		catch(MalformedJwtException ex) {
			System.out.println("Inavlid JWT Token");
		}
		catch(ExpiredJwtException ex) {
			System.out.println("JWT Token expired");
		}
		catch(UnsupportedJwtException ex) {
			System.out.println("Unsupported Jwt Token");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("JWT Claims string empty");
		}
		return false;
	}
	
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");
		return Long.parseLong(id);
	}
}
