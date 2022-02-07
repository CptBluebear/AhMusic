package org.corodiak.ahmusic.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	public static final String key = "TESTKEY";
	
	public String createToken(Map<String, Object> payload) {
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");

		Long expiredTime = 1000 * 60l * 60l * 365l;
		Date now = new Date();
		now.setTime(now.getTime() + expiredTime);

		String jwt = Jwts.builder().setHeader(header).setClaims(payload).setExpiration(now)
				.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();

		return jwt;
	}
	
	public Claims validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
			return claims;
		} catch (Exception e) {
			return null;
		}
	}
	
}
