package com.myproject.myproject.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.myproject.myproject.model.User;

public class CustomUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

    public CustomUserDetails(User user){
    	this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    { 
    	if(Objects.nonNull(user.getRole()))
    	{
    		return Collections.singleton(new SimpleGrantedAuthority(user.getRole())); 
    	}
    	return Collections.singleton(new SimpleGrantedAuthority("USER")); 
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUserName(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}

