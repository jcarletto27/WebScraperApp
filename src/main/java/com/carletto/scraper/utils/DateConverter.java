package com.carletto.scraper.utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jcarlett on 3/10/2017.
 */
public class DateConverter extends StringConverter<LocalDate> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    @Override
    public String toString(LocalDate object) {
        if (object != null) {
            return formatter.format(object);

        } else {
            return null;
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, formatter);
        } else {
            return null;
        }
    }
}