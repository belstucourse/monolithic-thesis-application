package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.FileType;
import com.belstu.thesisproject.dto.user.PsychologistDto;
import com.belstu.thesisproject.dto.user.UserDto;
import com.belstu.thesisproject.exception.UserNotFoundException;
import com.belstu.thesisproject.mapper.UserMapper;
import com.belstu.thesisproject.service.AmazonService;
import com.belstu.thesisproject.service.UserService;
import com.belstu.thesisproject.valiadator.OnCreate;
import com.belstu.thesisproject.valiadator.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.belstu.thesisproject.dto.user.UserRole.ROLE_CLIENT;
import static java.util.Collections.singleton;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/users")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AmazonService amazonService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable final String id) throws UserNotFoundException {
        return userMapper.map(userService.getUserById(id));
    }

    @GetMapping("/doctors")
    public Page<PsychologistDto> getPsychologists(@RequestParam List<String> tagNames, @PageableDefault(
            sort = {"id"},
            direction = DESC) Pageable pageable) {
        final Page<Psychologist> psychologists = userService.getPsychologistsByTagNames(tagNames, pageable);
        final Page<PsychologistDto> psychologistDtos = psychologists.map(userMapper::map);
        return psychologistDtos;
    }

    @GetMapping("/all-psycho")
    public List<PsychologistDto> getPsychologists() {
        final List<Psychologist> psychologists = userService.getAllPsychologist();
        List<PsychologistDto> res = userMapper.mapToDtoListP(psychologists);
        for (PsychologistDto p: res) {
            p.setSertificateUrl(amazonService.getSertificateUrl(p.getId(), FileType.CERTIFICATE));
        }
        return res;
    }

    @GetMapping
    public UserDto getUserByEmail(@RequestParam final String email) throws UserNotFoundException {
        return userMapper.map(userService.getUserByEmail(email));
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() throws UserNotFoundException {
        return userMapper.mapToDtoList(userService.getAllUsers());
    }

    @PostMapping
    @Validated(OnCreate.class)
    public UserDto saveUser(@RequestBody @Valid final UserDto userDto) {
        final User user = userMapper.map(userDto);
        user.setRoles(singleton(ROLE_CLIENT));
        return userMapper.map(userService.save(user));
    }

    @PutMapping
    @Validated(OnUpdate.class)
    public UserDto updateUser(@RequestBody @Valid final UserDto userDto)
            throws UserNotFoundException {
        final User user = userMapper.map(userDto);
        return userMapper.map(userService.update(user));
    }

    @PutMapping("/psycho")
    @Validated(OnUpdate.class)
    public PsychologistDto updatePsychologist(@RequestBody @Valid final PsychologistDto psychologistDto)
            throws UserNotFoundException {
        final Psychologist psychologist = userMapper.map(psychologistDto);
        return userMapper.map(userService.update(psychologist));
    }

    @PatchMapping
    public UserDto patchUser(@RequestBody final UserDto userDto) throws UserNotFoundException {
        final User user = userMapper.map(userDto);
        return userMapper.map(userService.patch(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = OK)
    public void deleteById(@PathVariable final String id) throws UserNotFoundException {
        userService.delete(id);
    }
}
