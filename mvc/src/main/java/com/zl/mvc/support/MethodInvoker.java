package com.zl.mvc.support;


import com.zl.mvc.argument.HandlerMethodArgumentResolverComposite;
import com.zl.mvc.argument.MethodParameter;
import com.zl.mvc.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 这是一个通用的方法调用类型，2参数的invoke方法是调用静态方法用的，
 * 3参数的invoke方法是调用实例方法用的
 */
public class MethodInvoker {

    private final HandlerMethodArgumentResolverComposite resolvers ;

    public MethodInvoker(HandlerMethodArgumentResolverComposite resolvers) {
        this.resolvers = resolvers;
    }

    /**
     * 这是处理静态方法调用的
     * @param method 代表一个静态方法
     * @param request 数据来源
     * @return
     * @throws Exception
     */
    public Object invoke(Method method, HttpServletRequest request) throws Exception {
        return invoke(null, method, request);
    }

    /**
     * 这是处理实例方法调用的
     * @param instance:实例方法所属的对象
     * @param method:代表一个实例方法
     * @param request:数据源
     * @return
     * @throws Exception
     */
    public Object invoke(Object instance, Method method, HttpServletRequest request) throws Exception {
        List<String> paramNames = ReflectionUtils.getParameterNames(method);
        int paramCount = method.getParameterCount();
        Object[] paramValues = new Object[paramCount];

        for (int i = 0; i < paramCount; i++) {
            String paramName = paramNames.get(i);
            MethodParameter methodParameter = new MethodParameter(method, i, paramName);
            paramValues[i] = resolvers.resolveArgument(methodParameter,request) ;
        }
        return method.invoke(instance, paramValues);
    }
}
