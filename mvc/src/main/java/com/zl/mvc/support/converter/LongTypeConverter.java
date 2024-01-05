package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class LongTypeConverter implements WebTypeConverter {
    @Override
    public Long convert(String paramValue)throws Exception {
        return Long.valueOf(paramValue);
    }
}
