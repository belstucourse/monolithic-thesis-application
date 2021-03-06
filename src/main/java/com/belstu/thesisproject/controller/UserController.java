package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.user.Client;
import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.FileType;
import com.belstu.thesisproject.dto.user.PsychologistDto;
import com.belstu.thesisproject.dto.user.UserDto;
import com.belstu.thesisproject.exception.NotFoundException;
import com.belstu.thesisproject.mapper.UserMapper;
import com.belstu.thesisproject.service.AmazonService;
import com.belstu.thesisproject.service.UserService;
import com.belstu.thesisproject.valiadator.OnCreate;
import com.belstu.thesisproject.valiadator.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.belstu.thesisproject.dto.user.UserRole.ROLE_CLIENT;
import static java.util.Collections.singleton;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/users")
@Validated
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AmazonService amazonService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable final String id) throws NotFoundException {
        final User user = userService.getUserById(id);

        final String sertificateUrl = amazonService.getSertificateUrl(user.getId(), FileType.AVATAR);
        if (user instanceof Client) {
            ((Client) user).setAvatarUrl(sertificateUrl);
        }
        if (user instanceof Psychologist) {
            ((Psychologist) user).setAvatarUrl(sertificateUrl);
        }
        return userMapper.map(user);
    }

    @GetMapping("/doctors")
    public Page<PsychologistDto> getPsychologists(@RequestParam List<String> tagNames, @PageableDefault(
            sort = {"id"},
            direction = DESC) Pageable pageable) {
        final Page<Psychologist> psychologists = userService.getPsychologistsByTagNames(tagNames, pageable);
        final Page<PsychologistDto> psychologistDtos = psychologists.map(userMapper::map);
        psychologistDtos.map(psychologistDto -> {
            final String sertificateUrl = amazonService.getSertificateUrl(psychologistDto.getId(), FileType.AVATAR);
            psychologistDto.setAvatarUrl(sertificateUrl);
            return psychologistDto;
        });
        return psychologistDtos;
    }

    @GetMapping("/all-psycho")
    public List<PsychologistDto> getPsychologists() {
        final List<Psychologist> psychologists = userService.getAllPsychologist();
        List<PsychologistDto> res = userMapper.mapToDtoListP(psychologists);
        for (PsychologistDto p : res) {
            p.setSertificateUrl(amazonService.getSertificateUrl(p.getId(), FileType.CERTIFICATE));
            p.setAvatarUrl(amazonService.getSertificateUrl(p.getId(), FileType.AVATAR));
        }
        return res;
    }

    @GetMapping
    public UserDto getUserByEmail(@RequestParam final String email) throws NotFoundException {
        return userMapper.map(userService.getUserByEmail(email));
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() throws NotFoundException {
        final List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> {
            final String sertificateUrl = amazonService.getSertificateUrl(user.getId(), FileType.AVATAR);
            if (user instanceof Client) {
                ((Client) user).setAvatarUrl(sertificateUrl);
            }
            if (user instanceof Psychologist) {
                ((Psychologist) user).setAvatarUrl(sertificateUrl);
            }
        });
        return userMapper.mapToDtoList(allUsers);
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
            throws NotFoundException {
        final User user = userMapper.map(userDto);
        return userMapper.map(userService.update(user));
    }

    @PutMapping("/psycho")
    @Validated(OnUpdate.class)
    public PsychologistDto updatePsychologist(@RequestBody @Valid final PsychologistDto psychologistDto)
            throws NotFoundException {
        final Psychologist psychologist = userMapper.map(psychologistDto);
        return userMapper.map(userService.update(psychologist));
    }

    @PatchMapping
    public UserDto patchUser(@RequestBody final UserDto userDto) throws NotFoundException {
        final User user = userMapper.map(userDto);
        return userMapper.map(userService.patch(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = OK)
    public void deleteById(@PathVariable final String id) throws NotFoundException {
        userService.delete(id);
    }

    @GetMapping("/doctors/search")
    public Page<PsychologistDto> getPsychologistsByTagNamesAndWorkday(@RequestParam List<String> tagNames,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                      @PageableDefault(
                                                                              sort = {"id"},
                                                                              direction = DESC) Pageable pageable) {
        final Page<Psychologist> psychologists = userService.getPsychologistsByTagNamesAndWorkday(tagNames, startDate, endDate, pageable);
        final Page<PsychologistDto> psychologistDtos = psychologists.map(userMapper::map);
        psychologistDtos.map(psychologistDto -> {
            final String sertificateUrl = amazonService.getSertificateUrl(psychologistDto.getId(), FileType.AVATAR);
            psychologistDto.setAvatarUrl(sertificateUrl);
            return psychologistDto;
        });
        return psychologistDtos;
    }
}
