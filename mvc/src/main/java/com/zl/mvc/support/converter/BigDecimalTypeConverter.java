package com.zl.mvc.support.converter;

import com.zl.mvc.support.WebTypeConverter;

import java.math.BigDecimal;


public class BigDecimalTypeConverter implements WebTypeConverter {

    @Override
    public BigDecimal convert(String paramValue) throws Exception {
        return new BigDecimal(paramValue);
    }
}
