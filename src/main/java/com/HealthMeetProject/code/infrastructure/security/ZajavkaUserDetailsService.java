package com.HealthMeetProject.code.infrastructure.security;

import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ZajavkaUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if(Objects.isNull(user)){
            throw new NotFoundException("User with email [%s] and given password doesn't exist".formatted(email));
        }
        List<SimpleGrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }
    private List<SimpleGrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .distinct()
                .toList();
    }
    private UserDetails buildUserForAuthentication(
            UserEntity user,
            List<SimpleGrantedAuthority> authorities
    ) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities
        );
    }
}
