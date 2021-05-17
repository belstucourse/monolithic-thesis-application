package com.belstu.thesisproject.util;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public final class TimeIntervalUtils {

  private TimeIntervalUtils() {}

  public static boolean validateTimeInterval(
      LocalDateTime startDateTime, LocalDateTime endDateTime) {
    if (isNull(startDateTime) || isNull(endDateTime)) {
      return false;
    }
    return startDateTime.isBefore(endDateTime);
  }

  public static boolean dateIsBeforeOrEqual(LocalDate date, LocalDate dateForCompare) {
    return date.isBefore(dateForCompare) || date.isEqual(dateForCompare);
  }

  public static boolean dateTimeIsBeforeOrEqual(
      LocalDateTime dateTime, LocalDateTime dateTimeForCompare) {
    return dateTime.isBefore(dateTimeForCompare) || dateTime.isEqual(dateTimeForCompare);
  }

  public static boolean dateTimeIsAfterOrEqual(
      ZonedDateTime dateTime, ZonedDateTime dateTimeForCompare) {
    return dateTime.isAfter(dateTimeForCompare) || dateTime.isEqual(dateTimeForCompare);
  }

  public static boolean dateTimeIsInRange(
      final LocalDateTime dateTime, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    return (dateTime.isBefore(endDateTime) && dateTime.isAfter(startDateTime))
        || (dateTime.isEqual(endDateTime) || dateTime.isEqual(startDateTime));
  }
}
