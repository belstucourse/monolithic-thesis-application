package com.belstu.thesisproject.security;

import com.belstu.thesisproject.domain.user.Role;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.user.UserRole;
import com.belstu.thesisproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userService.getUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), convertRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> convertRoles(
            final Collection<Role> roles) {
        final String[] roleNames = roles.stream().map(Role::getUserRole).map(UserRole::name).toArray(String[]::new);
        return createAuthorityList(roleNames);
    }
}
