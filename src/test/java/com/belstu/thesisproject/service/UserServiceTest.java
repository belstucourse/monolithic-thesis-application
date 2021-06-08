package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.Tag;
import com.belstu.thesisproject.repository.PsychologistRepository;
import com.belstu.thesisproject.repository.TagRepository;
import com.belstu.thesisproject.repository.UserRepository;
import com.belstu.thesisproject.service.impl.UserServiceImpl;
import com.belstu.thesisproject.updater.UserUpdater;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    UserUpdater userUpdater;
    @Mock
    RoleService roleService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TagRepository tagRepository;
    @Mock
    PsychologistRepository psychologistRepository;

    @Test
    public void create_givenNotValidUser_shouldNotThrowException() {
        final Psychologist psychologist = new Psychologist();
        assertDoesNotThrow(() -> userRepository.save(psychologist));
    }
}
