package br.com.dashgo.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.dashgo.property.JwtConfiguration;
import br.com.dashgo.security.util.JWTUtils;
import br.com.dashgo.security.util.SecurityContextUtil;

public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired private JwtConfiguration jwtConfiguration;
    @Autowired private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
    	String requestURI = request.getRequestURI();
    	if(requestURI.contains("/auth/signin") || requestURI.contains("/auth/refreshtoken")) {
    		chain.doFilter(request, response);
            return;
    	}
    	
    	String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(jwtConfiguration.getTokenType())) {
            chain.doFilter(request, response);
            return;
        }
        
        String token = header.replace(jwtConfiguration.getTokenType(), "").trim();

    	SecurityContextUtil.setSecurityContext(jwtUtils.getClaims(token));
        
        chain.doFilter(request, response);
    }

}
