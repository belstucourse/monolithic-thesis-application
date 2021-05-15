package com.belstu.thesisproject.util;

import java.time.LocalDateTime;

public class RoundUtils {
    private static final double TWENTY_MIN = 20.0;
    private static final int ONE_HOUR = 1;
    private static final int SIXTY_MIN = 60;
    private static final int ZERO_MIN = 0;
    private static final int ZERO_SECOND = 0;
    private static final int ZERO_NANO = 0;

    private RoundUtils() {
    }

    public static LocalDateTime roundMinToTwenty(LocalDateTime dateTime) {
        int minutes = (int) (Math.ceil(dateTime.getMinute() / TWENTY_MIN) * TWENTY_MIN);
        if (minutes >= SIXTY_MIN) {
            return dateTime.plusHours(ONE_HOUR).withMinute(ZERO_MIN)
                    .withSecond(ZERO_SECOND)
                    .withNano(ZERO_NANO);
        }
        return dateTime.withMinute(minutes)
                .withSecond(ZERO_SECOND)
                .withNano(ZERO_NANO);
    }
}
