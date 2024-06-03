package com.example.api_gateway_news_app.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
import lombok.Data;

// @Entity
@Data
// @Table(name = "users")
public class User implements UserDetails {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;
    String email;
    String password;
    String role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        // If you want the user to have multiple roles, just add another role :
        // Like this : return List.of(new SimpleGrantedAuthority(role.name()), new SimpleGrantedAuthority("ADMIN"));
        return List.of(new SimpleGrantedAuthority(this.role));
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return email;
    }
}
