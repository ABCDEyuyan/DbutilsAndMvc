package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class IntegerTypeConverter implements WebTypeConverter{
    @Override
    public Integer convert(String paramValue)throws Exception {
        return Integer.valueOf(paramValue);
    }
}
