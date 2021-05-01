package com.belstu.thesisproject.updater;

import com.belstu.thesisproject.domain.user.User;

public interface UserUpdateVisitor<T extends User> {
  T update(final UserUpdater userUpdater, T newUser);
}
