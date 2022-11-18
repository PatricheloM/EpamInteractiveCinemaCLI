package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.util.converter.LocalDateTimeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeConverterTest {

    @Test
    public void testShouldReturnFormattedStringWhenFormattedGivenLocalDateTime() {

        // Given
        LocalDateTime ldt = LocalDateTime.of(2022, 11, 11, 10, 13);

        // When
        String result = LocalDateTimeConverter.localDateTimeToString(ldt);

        // Then
        Assertions.assertEquals("2022-11-11 10:13", result);
    }

    @Test
    public void testShouldReturnLocalDateTimeWhenParsingGivenACorrectString() {

        // Given
        String s = "2021-05-12 15:03";

        // When
        LocalDateTime result = LocalDateTimeConverter.stringToLocalDateTime(s);

        // Then
        Assertions.assertEquals(LocalDateTime.of(2021, 5, 12, 15, 3), result);
    }

    @Test
    public void testShouldThrowExceptionWhenParsingGivenAIncorrectString() {

        // Given
        String s = "2021/05/12 15:03";

        // When
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            LocalDateTime result = LocalDateTimeConverter.stringToLocalDateTime(s);
        });

        // Then
    }
}
