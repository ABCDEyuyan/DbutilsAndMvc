package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class FloatTypeConverter implements WebTypeConverter {

    @Override
    public Float convert(String paramValue) throws Exception {
        return Float.valueOf(paramValue);
    }
}
