package com.zl.mvc;

import com.zl.mvc.argument.MethodParameter;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理者的方法参数的解析器，web开发环境下，数据都是来自于HttpServletRequest对象，
 * 其典型的使用方式如下：
 * <pre class="code">
 *   for(MethodArgumentResolver resolver:resolvers){
 *     if(resolver.supports(parameter){
 *       Object value = resolver.resolveArgument(parameter,request);
 *     }
 *   }
 * </pre>
 * <p>只有在supports方法返回true的情况下才会调用resolveArgument进行参数解析,
 * 如果参数类型没有一个解析器可以解析，那么通常就会抛出异常，见{@link com.zl.mvc.argument.HandlerMethodArgumentResolverComposite#resolveArgument(MethodParameter, HttpServletRequest)}</p>
 *
 * <p>解析通常是基于方法的参数名从请求中获取对象的字符串数据或者Part类型的数据，这个参数名是利用反射的方式获取,
 * 所以编译项目时需要添加-parameters编译选项，如果不想用这种方式获取参数名或者想指定别名，可以通过注解RequestParam的value属性来实现
 * </p>
 * @see MethodParameter
 * @see com.zl.mvc.argument.HandlerMethodArgumentResolverComposite
 * @see com.zl.mvc.argument.RequestParam
 * @see com.zl.mvc.argument.RequestBodyMethodArguementResolver
 * @see com.zl.mvc.argument.MultipartFileMethodArgumentResolver
 * @see com.zl.mvc.argument.SimpleTypeMethodArguementResolver
 * @see com.zl.mvc.argument.ComplexTypeMethodArgumentResolver
 */
public interface MethodArgumentResolver {

    /**
     * 用来判断此解析器是否支持对此参数的解析
     * @param parameter：封装了某一个方法的参数信息
     * @return true表示支持对此参数的解析
     */
    boolean supports(MethodParameter parameter);

    /**
     * 通常是基于方法参数的类型与名称从请求传递过来的字符串解析出兼容类型的值
     * @param parameter
     * @param request
     * @return 返回兼容类型的值，也可能返回null值，null并不表示此解析器解析不了，这点与HandlerMapping是不一样的,<br/>
     * 请求端没有传递过来数据就有可能解析结果为null
     * @throws Exception 通常是请求端有数据，但无法解析成兼容类型的参数值时就抛出异常，建议抛一个RuntimeException
     */
    Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception;
}
