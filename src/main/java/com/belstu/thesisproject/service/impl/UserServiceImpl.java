package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.Tag;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.exception.UserNotFoundException;
import com.belstu.thesisproject.repository.PsychologistRepository;
import com.belstu.thesisproject.repository.TagRepository;
import com.belstu.thesisproject.repository.UserRepository;
import com.belstu.thesisproject.service.RoleService;
import com.belstu.thesisproject.service.UserService;
import com.belstu.thesisproject.updater.UserUpdater;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserUpdater userUpdater;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TagRepository tagRepository;
    private final PsychologistRepository psychologistRepository;

    @Override
    public User getUserById(@NotNull final String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User save(@NotNull final User user) {
        populateTagsVetIfNeeded(user);
        roleService.assignRoleToUser(user);
        encodePassword(user);
        return userRepository.save(user);
    }

    private void populateTagsVetIfNeeded(final User user) {
        if (user instanceof Psychologist) {
            final Psychologist psychologist = (Psychologist) user;
            final Set<Tag> persistedTags =
                    psychologist.getTags().stream()
                            .map(tag -> tagRepository.findByName(tag.getName()).orElse(tag))
                            .collect(toSet());
            final HashSet<Tag> tags = new HashSet<>(tagRepository.saveAll(persistedTags));
            psychologist.setTags(tags);
        }
    }

    @Override
    public User getUserByEmail(@NotBlank final String username) throws UserNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(username));
    }

    @Override
    @Transactional
    public User update(@NotNull final User newUser) throws UserNotFoundException {
        final String userId = newUser.getId();
        final User existingUser =
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final String newPassword = newUser.getPassword();
        if (StringUtils.isNoneEmpty(newPassword)) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }
        return existingUser.update(userUpdater, newUser);
    }

    public void delete(@NotNull final String id) throws UserNotFoundException {
        userRepository.deleteById(id);
    }

    @Override
    public User patch(@NotNull User user) {
        return null;
    }

    private void encodePassword(final User user) {
        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    @Override
    public boolean existsById(@NotNull final String id) {
        return userRepository.existsById(id);
    }

    @Override
    public Page<Psychologist> getPsychologistsByTagNames(final List<String> tagNames, Pageable pageable) {
        final List<Tag> persistedTags = tagRepository.findByNameIn(tagNames);
        return psychologistRepository.findDistinctByTagsIn(persistedTags, pageable);
    }
}
