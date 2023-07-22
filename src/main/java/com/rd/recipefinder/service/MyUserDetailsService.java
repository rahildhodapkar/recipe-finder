package com.rd.recipefinder.service;

import com.rd.recipefinder.model.RolesEntity;
import com.rd.recipefinder.model.SecurityUserEntity;
import com.rd.recipefinder.model.UserEntity;
import com.rd.recipefinder.repository.RolesRepository;
import com.rd.recipefinder.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public MyUserDetailsService(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found:" + username));
        RolesEntity rolesEntity = rolesRepository.findByUsername(username);
        return new SecurityUserEntity(userEntity, rolesEntity);
    }
}
