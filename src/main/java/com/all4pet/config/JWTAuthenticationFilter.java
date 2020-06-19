package com.all4pet.config;
 
import java.io.IOException;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.all4pet.controller.UserDetailsServiceImp;

import io.jsonwebtoken.ExpiredJwtException;
 
public class JWTAuthenticationFilter extends OncePerRequestFilter  {
	@Autowired
     JWTTokenProvider tokenProvider;
	@Autowired 
	 UserDetailsServiceImp userDetailsServiceImp;
	@Autowired 
	private JWTTokenProvider jwtTokenProvider;
	@Override
    public void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String userName = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				 userName = tokenProvider.getUserNameFromJWT(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.warn("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.warn("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(userName);
            if (jwtTokenProvider.validateToken(jwtToken, userDetails))  {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
        filterChain.doFilter(request, response);
    }

//    public String getJwtFromRequest(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        // Kiểm tra xem header Authorization có chứa thông tin jwt không
//        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
//            return "";
//        } else {
//        	token =  token.substring(7);
//        }
//        return token;
//    }

}
