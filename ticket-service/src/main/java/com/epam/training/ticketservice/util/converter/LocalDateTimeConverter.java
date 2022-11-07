package com.epam.training.ticketservice.util.converter;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String localDateTimeToString(LocalDateTime ldt) throws DateTimeException {
        return ldt.format(formatter);
    }

    public static LocalDateTime stringToLocalDateTime(String s) throws DateTimeParseException {
        return LocalDateTime.parse(s, formatter);
    }
}
