package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    User getUserById(@NotNull final String id) throws NotFoundException;

    User save(@NotNull final User user);

    User getUserByEmail(@NotBlank final String username) throws NotFoundException;

    User update(@NotNull final User user) throws NotFoundException;

    Psychologist update(@NotNull final Psychologist psychologist) throws NotFoundException;

    void delete(@NotNull final String id) throws NotFoundException;

    User patch(@NotNull final User user);

    boolean existsById(@NotNull final String id);

    Page<Psychologist> getPsychologistsByTagNames(List<String> tagNames, Pageable pageable);

    List<User> getAllUsers();

    List<Psychologist> getAllPsychologist();

    Page<Psychologist> getPsychologistsByTagNamesAndWorkday(List<String> tagNames, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
