package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.exception.UserNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface UserService {
  User getUserById(@NotNull final String id) throws UserNotFoundException;

  User save(@NotNull final User user);

  User getUserByEmail(@NotBlank final String username) throws UserNotFoundException;

  User update(@NotNull final User user) throws UserNotFoundException;

  void delete(@NotNull final String id) throws UserNotFoundException;

  User patch(@NotNull final User user);

  boolean existsById(@NotNull final String id);
}
