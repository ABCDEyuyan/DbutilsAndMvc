package com.zl.mvc.adapter;

import com.zl.mvc.HandlerAdapter;
import com.zl.mvc.ViewResult;
import com.zl.mvc.argument.HandlerMethodArgumentResolverComposite;
import com.zl.mvc.handler.HandlerMethod;
import com.zl.mvc.support.MethodInvoker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.zl.mvc.ViewResult.adaptHandlerResult;

/**
 * 此类是Mvc框架的核心HandlerAdapter,此适配器主要是利用Mvc框架注册的所有解析器对{@link HandlerMethod}封装的请求处理方法
 * 的参数进行解析，解析之后再调用方法以处理请求
 * <p>此HandlerAdapter对Handler的返回类型会进行适配，适配的逻辑见{@link ViewResult#adaptHandlerResult(Object)},
 * 详细解释见{@link HandlerAdapter}的注释说明</p>
 * @see HandlerAdapter
 * @see HandlerMethodArgumentResolverComposite
 * @see MethodInvoker
 * @see HandlerMethod
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

    private static final HandlerMethodArgumentResolverComposite defaultResolvers = HandlerMethodArgumentResolverComposite.defaultInstance();

    private final HandlerMethodArgumentResolverComposite resolvers;
    private final MethodInvoker methodInvoker;

    public RequestMappingHandlerAdapter() {
        this(defaultResolvers);
    }

    public RequestMappingHandlerAdapter(HandlerMethodArgumentResolverComposite resolvers) {
        this.resolvers = resolvers;
        methodInvoker = new MethodInvoker(resolvers);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod &&
                ((HandlerMethod) handler).getHandlerMethod() != null;
    }

    public ViewResult handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object instance = handlerMethod.getHandlerObject();
        Method method = handlerMethod.getHandlerMethod();

        Object handlerResult = methodInvoker.invoke(instance, method, req);
        return adaptHandlerResult(handlerResult);

    }
}
