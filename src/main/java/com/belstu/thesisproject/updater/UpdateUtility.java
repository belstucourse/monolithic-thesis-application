package com.belstu.thesisproject.updater;

import static java.util.Objects.isNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUtility<T> {
  private final T value;
  private Boolean dirty;

  public static <T> UpdateUtility<T> start(final T value) {
    return new UpdateUtility<>(value);
  }

  public <E> UpdateUtility<T> update(final Supplier<E> getter, final Consumer<E> setter) {
    final E newValue = getter.get();
    if (isNull(newValue)) {
      return this;
    }

    setter.accept(newValue);
    dirty = true;
    return this;
  }

  public <E> UpdateUtility<T> update(final Supplier<E> getter, final Predicate<E> operation) {
    final E newValue = getter.get();
    if (isNull(newValue)) {
      return this;
    }

    final boolean updated = operation.test(newValue);
    dirty |= Boolean.TRUE.equals(updated);
    return this;
  }

  public T save(final UnaryOperator<T> saveAction) {
    if (dirty) {
      return saveAction.apply(value);
    }

    return value;
  }
}
