package com.zl.mvc.exception;

import com.zl.mvc.HandlerExceptionResolver;
import com.zl.mvc.ViewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此类是一个演示用的异常解析器，没什么特别大的用处
 */
public class LogHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ViewResult resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(ex instanceof RuntimeException)) {
            return null;
        }
        //我们这个解析器只解析RuntimeException
        System.out.println("\n==============");
        System.out.println("异常消息是:" + ex.getMessage());
        System.out.println("==============");

        return null;
    }
}
