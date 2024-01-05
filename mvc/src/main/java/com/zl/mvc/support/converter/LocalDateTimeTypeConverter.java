package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeConverter implements WebTypeConverter {

    @Override
    public LocalDateTime convert(String paramValue) throws Exception {
        return LocalDateTime.parse(paramValue, DateTimeFormatter.ofPattern(DateTypeConverter.DATETIMEPATTERN));
    }
}
