package com.belstu.thesisproject.mapper;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MapperErrorMessageKeys {
  public static final String CAST_EXCEPTION_MESSAGE_KEY =
      "%s with %s id hasn't been cast to Entity";
}
