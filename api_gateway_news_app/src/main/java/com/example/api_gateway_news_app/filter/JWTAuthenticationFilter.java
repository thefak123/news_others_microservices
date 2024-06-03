package com.example.api_gateway_news_app.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.api_gateway_news_app.model.Comment;
import com.example.api_gateway_news_app.model.Response;
// import com.example.api_gateway_news_app.repository.UserRepository;
import com.example.api_gateway_news_app.service.JWTService;
import com.example.api_gateway_news_app.service.UserService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
// OncePerRequestFilter means the request only be intercepted once
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    // @Autowired
    // private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }else{
            // Get only the token
            String token = authorization.substring(7);
            String email = "";
            
            try{
                email = jwtService.extractEmail(token);
                
                
            }catch (JwtException e) {
                // Handle the JWT exception
           
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token: " + e.getMessage());
           
                return;
            }
            
            
            if (email == null){
                
                filterChain.doFilter(request, response);
                return;
            }
            //SecurityContextHolder.getContext().getAuthentication().isAuthenticated() this won't cause all of other requests becomes true, after first valid request
            // Because after first user has logged in, spring will send the cookies to the browser and that cookie will be sent on every next requests that
            // the user will be made (the  spring will remember the cookies, because it saves the user session) 
            // (remember ! HttpServletRequest request will be different on every request)
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userService.loadUserByUsername(email);
                
                if(jwtService.isValid(token, userDetails)){
                        // Basically after user has logged in, the informations will be saved in the SecurityContextHolder
                        // Then every time the new request comes in and SecurityContextHolder already has the token, then the user can directly access the API 
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // Probably this set additional details or fields into auth token
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
            

        }
        
    }
    
}
