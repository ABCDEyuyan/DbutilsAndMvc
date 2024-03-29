package com.zl.mvc.argument;

import com.zl.mvc.MethodArgumentResolver;
import com.zl.mvc.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 此抽象解析器是那些支持对某个类型T，T[],List<T>这样情况的解析器继承用的，比如MultipartFile，MultipartFile[],List<MultipartFile>这样的情况，
 * 并不要求所有的参数解析器继承此类型，比如@RequestBody修饰的参数的解析器就完全没有必要继承此类型,servlet Api的参数解析器也不需要继承此类
 *
 * @see RequestBodyMethodArguementResolver
 * @see MultipartFileMethodArgumentResolver
 * @see SimpleTypeMethodArguementResolver
 * @see ServletApiMethodArgumentResolver
 */
public abstract class AbstractCommonTypeMethodArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean supports(MethodParameter parameter) {
        return isSupportedType(parameter) ||
                isSupportedTypeArray(parameter) ||
                isSupportedTypeList(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) throws Exception {
        /*  请求中根本没有对应参数名的请求数据时，source可能就是null的
            这里不直接依据source为null值return结束方法的执行，是因为简单类型参数解析器还需要对这些null值进行一些额外的逻辑处理
            数据源为null的处理逻辑应该由具体的子类去处理，并不应该在父类这里处理
         */
        Object[] source = getSource(parameter, request);
        //如果source为null，会返回一个长度为0的空数组，toObjectArray方法并不是一定要调用的，调用可以避免空引用与数组索引越界的异常
        Object[] objectArray = ObjectUtils.toObjectArray(source);
        int length = Array.getLength(objectArray);
        if (isSupportedType(parameter)) {
            return resolveArgumentInternal(parameter.getParameterType(), length == 0 ? null : objectArray[0], parameter);
        } else if (isSupportedTypeArray(parameter)) {
            //如果数据源为null，那么length就=0，那么array就是一个长度为0的数组，并不会进入到循环里面执行真正的参数处理
            Object array = Array.newInstance(parameter.getComponentType(), length);
            for (int i = 0; i < length; i++) {
                Array.set(array, i, resolveArgumentInternal(parameter.getComponentType(), Array.get(objectArray, i), parameter));
            }
            return array;
        } else if (isSupportedTypeList(parameter)) {
            List list = new ArrayList();
            for (int i = 0; i < length; i++) {
                list.add(resolveArgumentInternal(parameter.getFirstActualTypeArgument(), Array.get(objectArray, i), parameter));
            }
            return list;
        }
        return null;
    }

    protected abstract boolean supportsInternal(Class<?> type);

    protected abstract Object resolveArgumentInternal(Class<?> type, Object parameterValue, MethodParameter methodParameter) throws Exception;

    /**
     * 此方法用来获取请求中的数据源的，数据源主要是request.getParameterValues(name)与request.getParts()两大类<br/>
     * 这里通过这个方法抽象化了2种不同的数据获取方式
     * @param methodParameter：方法参数，利用它可以获取参数名之内的信息
     * @param request：请求对象
     * @return: 通常返回某个key下得数组数据，即便只有一个数据，也以数组的形式返回
     */
    protected abstract Object[] getSource(MethodParameter methodParameter, HttpServletRequest request);

    private boolean isSupportedType(MethodParameter methodParameter) {
        return supportsInternal(methodParameter.getParameterType());
    }

    private boolean isSupportedTypeArray(MethodParameter methodParameter) {
        return methodParameter.isArray() && supportsInternal(methodParameter.getComponentType());
    }

    private boolean isSupportedTypeList(MethodParameter methodParameter) {
        return methodParameter.isList() && supportsInternal(methodParameter.getFirstActualTypeArgument());
    }
}
