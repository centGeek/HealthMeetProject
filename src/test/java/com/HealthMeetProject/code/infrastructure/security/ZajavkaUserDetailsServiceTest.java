package com.HealthMeetProject.code.infrastructure.security;

import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ZajavkaUserDetailsServiceTest {

    private ZajavkaUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new ZajavkaUserDetailsService(userRepository);
    }

    @Test
    void testLoadUserByUsername_ExistingUser() {
        // given
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setActive(true);

        RoleEntity role = new RoleEntity();
        role.setRole("ROLE_USER");

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        //then
        assertNotNull(userDetails);
        assertTrue(userDetails.isEnabled());
        assertEquals("test@example.com", userDetails.getUsername());

        @SuppressWarnings("unchecked") Set<SimpleGrantedAuthority> authoritiesSet = (Set<SimpleGrantedAuthority>) userDetails.getAuthorities();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(authoritiesSet);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.get(0).getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // when, then
        assertThrows(NotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistent@example.com"));
    }
}
