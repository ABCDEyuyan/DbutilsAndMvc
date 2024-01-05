package com.zl.mvc.support;

import com.zl.mvc.argument.MethodParameter;

import javax.servlet.http.HttpServletRequest;

/**
 * 类型转换器接口，主要是把web中的String数据类型转换为一些常见的基本类型及包装类型用，
 * 这些转换器通常被参数解析器使用(见：{@link com.zl.mvc.argument.SimpleTypeMethodArguementResolver#resolveArgument(MethodParameter, HttpServletRequest)})
 *
 * <p>
 *     建议:实现类不需要处理null值，转换过程中发生的异常转换为RuntimeException直接抛出即可
 * </p>
 *
 * @see com.zl.mvc.util.WebTypeConverterUtils
 * @see com.zl.mvc.argument.SimpleTypeMethodArguementResolver
 */
public interface WebTypeConverter {
   <T> T convert(String paramValue) throws Exception;
}