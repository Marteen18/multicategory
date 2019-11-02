package com.vyacheslavgoryunov.multicategory.service;

import com.vyacheslavgoryunov.multicategory.common.Auth;
import com.vyacheslavgoryunov.multicategory.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UsersService usersService;

    public UserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersService.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> authorities = Auth.getDefaultAuthorities();

        return new com.vyacheslavgoryunov.multicategory.common.UserDetails(user, authorities);
    }

}
