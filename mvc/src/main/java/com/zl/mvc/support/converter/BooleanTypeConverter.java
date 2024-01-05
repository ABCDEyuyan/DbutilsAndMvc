package com.zl.mvc.support.converter;


import com.zl.mvc.support.WebTypeConverter;

public class BooleanTypeConverter implements WebTypeConverter {

    @Override
    public Boolean convert(String paramValue) throws Exception {
        return Boolean.valueOf(paramValue);
    }
}
