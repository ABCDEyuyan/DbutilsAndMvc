package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class StringTypeConverter implements WebTypeConverter{

    @Override
    public String convert(String paramValue) throws Exception {
        return paramValue;
    }
}
