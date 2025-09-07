package com.shoppingcart.app.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.repositories.ClientRepository;

@Service
public class UserDetailImpl implements UserDetailsService{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client userDetails = this.clientRepository.findByUsername(username)
        .orElseThrow( () -> new UsernameNotFoundException("Username " + username + " does not exists."));
        
        Set<String> roles = new HashSet<>();
        roles.add(userDetails.getRole());
        Collection<? extends GrantedAuthority> authorities = roles.stream().map(
            role -> new SimpleGrantedAuthority("ROLE".concat(role)))
            .collect(Collectors.toSet());
        return new User(userDetails.getUsername(), userDetails.getPassword(), true, true, true, true, authorities);
    }
}
