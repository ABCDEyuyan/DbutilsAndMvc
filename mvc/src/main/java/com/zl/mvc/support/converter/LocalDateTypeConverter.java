package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeConverter implements WebTypeConverter{

    @Override
    public LocalDate convert(String paramValue) throws Exception {
        return LocalDate.parse(paramValue, DateTimeFormatter.ofPattern(DateTypeConverter.DATEPATTERN));
    }
}
