package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class ShortTypeConverter implements WebTypeConverter {

    @Override
    public Short convert(String paramValue) throws Exception {
        return Short.valueOf(paramValue);
    }
}
