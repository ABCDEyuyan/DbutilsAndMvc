package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

public class ByteTypeConverter implements WebTypeConverter {

    @Override
    public Byte convert(String paramValue) throws Exception {
        return Byte.valueOf(paramValue);
    }
}
