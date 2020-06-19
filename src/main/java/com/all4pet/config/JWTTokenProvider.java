package com.all4pet.config;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.all4pet.controller.CustomAuthenticationProvider;

import io.jsonwebtoken.*;

@Component

public class JWTTokenProvider implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3080330208400532748L;

	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "secret";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 15 * 60 * 60 * 1000;

    public String generateToken(String userName) {
        Date now = new Date();
        Date expiryDate = new Date(new Date().getTime() + JWT_EXPIRATION);
        
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                   .setSubject(userName)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

    
    public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromJWT(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
    
    //check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
	}
   
}