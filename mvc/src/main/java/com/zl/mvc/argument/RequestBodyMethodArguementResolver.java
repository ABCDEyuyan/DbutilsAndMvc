package com.zl.mvc.argument;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.zl.mvc.MethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.zl.mvc.util.JacksonUtils.fromJson;

/**
 * 此解析器只解析参数上有注解@RequestBody修饰的参数，
 * 普通的POJO类，Map,List集合类型是可以支持的，其它类型没有严格的测试过<br/>
 * 集合的反序列化参考了文章:<a href="https://stackoverflow.com/questions/9829403/deserialize-json-to-arraylistpojo-using-jackson">jackson反序列化为ArrayList</a>
 * @see RequestBody
 */
public class RequestBodyMethodArguementResolver implements MethodArgumentResolver {
    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.isPresent(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        if (parameter.isList()) {
            //假定方法参数是List<Emp>的话，那么下面的actualTypeArgument变量指的就是Emp
            Class<?> actualTypeArgument = parameter.getFirstActualTypeArgument();
            //可以理解为实例化一个集合，比如new ArrayList<Emp>();
            CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(List.class, actualTypeArgument);
            return fromJson(request.getInputStream(), collectionType);
        } else {
            return fromJson(request.getInputStream(), parameter.getParameterType());
        }
    }
}
