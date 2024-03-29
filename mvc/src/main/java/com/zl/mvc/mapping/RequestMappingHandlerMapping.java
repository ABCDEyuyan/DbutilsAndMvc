package com.zl.mvc.mapping;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zl.mvc.*;
import com.zl.mvc.handler.HandlerMethod;
import com.zl.mvc.support.AntPathMatcher;
import com.zl.mvc.support.PathMatcher;
import com.zl.mvc.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 此类是Mvc框架的核心{@link HandlerMapping}实现，其核心的功能有
 * <h3>什么是Handler</h3>
 * <p>所有扫描到的方法上有{@link RequestMapping}注解的都可以称之为Handler，如果类上也有此注解，
 * 那么就表明此Handler能处理的请求是由两段地址组成的，比如下面的handler能处理的地址就是<i>/product/list</i>
 * <pre class="code">
 *   &#64;RequestMapping("/product")
 *   public class ProductController{
 *       &#64;RequestMapping("/list")
 *       public ViewResult list(){...}
 *   }
 * </pre>
 * </p>
 * <h3>请求匹配</h3>
 * <p>此类实现借助{@link PathMatcher}可以实现灵活的地址匹配模式，比如区分大小写，不区分大小写，Ant地址模式匹配能，此类默认使用的AntPathMatcher。
 * 一般是在Handler类以及方法上通过注解{@link RequestMapping}指定地址方法，比如/**,然后依据当前请求的requestURI（去掉上下文）的地址进行模式匹配，
 * 能匹配上就返回此Handler作为处理者
 * </p>
 * <h3>缓存</h3>
 * <p>此类利用caffeine进行了缓存实现，会缓存100条url对应的Handler，避免每次请求过来都去查找Handler以提高性能
 * 整个Mvc框架在缓存上的应用的详细介绍见{@link com.zl.mvc.argument.HandlerMethodArgumentResolverComposite}</p>
 * @see com.zl.mvc.HandlerMapping
 * @see PathMatcher
 * @see AntPathMatcher
 * @see RequestMapping
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    private static final PathMatcher defaultPathMatcher = new AntPathMatcher.Builder().build();

    private Map<String, HandlerMethod> handlers = new HashMap<>();

    private PathMatcher pathMatcher = new AntPathMatcher.Builder().build();

    Cache<String, HandlerExecutionChain> cache = Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .build();

    public RequestMappingHandlerMapping() {
        resolveHandlers();
    }

    protected void resolveHandlers(){
        List<Class<?>> classList = MvcContext.getMvcContext().getAllScannedClasses();

        for (Class<?> clz : classList) {
            String urlInClass = getUrl(clz);
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    HandlerMethod handlerMethod = new HandlerMethod(method);
                    String urlInMethod = getUrl(method);
                    String url = urlInClass + urlInMethod;
                    addHandler(url, handlerMethod);
                }
            }
        }
    }
    protected void addHandler(String url, HandlerMethod handlerMethod){
        if(handlers.get(url)!=null){
            throw new IllegalStateException("不能有多个处理者对应同一个url");
        }
        this.handlers.put(url, handlerMethod);
    }
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String requestUrl = RequestUtils.getRequestUrl(request);
        /* get方法的第二个参数是在缓存中没有对应的key值执行的函数，其返回值会自动放置到缓存中
        * 如果返回值是null，那么不会放置到缓存中。
        * 所以，在这个案例中，如果url没有对应的handler，那么就返回null，cache中不会放置这个不存在url的缓存条目 */
        HandlerExecutionChain chain = cache.get(requestUrl,k->{
            HandlerMethod handler = getHandlerInternal(requestUrl);
            if (handler != null) {
               return new HandlerExecutionChain(handler, getInterceptors(request));
            }
            return null;
        });
        return chain;
    }

    protected HandlerMethod getHandlerInternal(String requestUrl) {
        HandlerMethod handler=null;

        Set<String> keys = handlers.keySet();
        List<String> patternKeys = new ArrayList<>(keys);
        Collections.sort(patternKeys,getPathMatcher().getPatternComparator(requestUrl));

        for (String key : patternKeys) {
            if (getPathMatcher().isMatch(key, requestUrl)) {
                handler = handlers.get(key);
                break;
            }
        }
        return handler;
    }
    /**
     * @param element AnnotatedElement类型代表着所有可以放置注解的元素，比如类，方法参数，字段等
     * @return 返回RequestMapping注解中指定的url值
     */
    private String getUrl(AnnotatedElement element) {
        return element.isAnnotationPresent(RequestMapping.class) ?
                element.getDeclaredAnnotation(RequestMapping.class).value() : "";
    }

    @Override
    public List<HandlerInterceptor> getInterceptors(HttpServletRequest request) {
        List<HandlerInterceptor> result = new ArrayList<>();
        List<HandlerInterceptor> interceptors = MvcContext.getMvcContext().getCustomHandlerInterceptors();
        String requestUrl = RequestUtils.getRequestUrl(request);
        for (HandlerInterceptor interceptor : interceptors) {
            Class<? extends HandlerInterceptor> interceptorClass = interceptor.getClass();
            if (interceptorClass.isAnnotationPresent(Interceptors.class)) {
                Interceptors annotation = interceptorClass.getDeclaredAnnotation(Interceptors.class);
                String[] includesPattern = annotation.value();
                String[] excludesPattern = annotation.excludePattern();
                if (shouldApply(requestUrl,includesPattern ) == true && shouldApply(requestUrl,excludesPattern) == false) {
                    result.add(interceptor);
                }
            }else{
                //没有注解修饰的拦截器被认为是拦截所有的请求，完全不理会当前请求url是什么
                result.add(interceptor);
            }
        }

        return result;
    }

    protected boolean shouldApply(String requestUrl,String... patterns) {
        boolean shouldApply = false;
        if (patterns == null ) {
            return false;
        }
        for (String pattern : patterns) {
            shouldApply= getPathMatcher().isMatch(pattern, requestUrl);
            if (shouldApply) {
                break;
            }
        }
        return shouldApply;
    }


    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }
}
