package com.marteen18.multicategory.common;

import com.marteen18.multicategory.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

public class Auth {
    public static boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

    public static UserDetails currentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            return null;

        return (UserDetails) authentication.getPrincipal();
    }

    public static Set<GrantedAuthority> getDefaultAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        return authorities;
    }

    public static void updateCurrentAuthentication(User user) {
        if (currentUserDetails().getId() != user.getId())
            return;

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        new UserDetails(user, getDefaultAuthorities()),
                        user.getPassword(), getDefaultAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
