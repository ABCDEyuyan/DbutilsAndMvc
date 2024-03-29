package com.zl.mvc.argument;

import com.zl.mvc.file.MultipartFile;
import com.zl.mvc.file.StandardMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 此类是用来处理文件上传的参数解析器，如果方法的参数是{@link MultipartFile} 或者{@link Part}类型以及它们的数组类型或List类型，
 * 此参数解析器就支持解析。
 * <p>在实际项目开发中，建议使用{@link MultipartFile}这个Mvc自己提供的类型，因为他提供了很多便利的方法，不推荐使用Part类型作为方法参数使用</p>
 *
 * @see com.zl.mvc.util.FileCopyUtils
 * @see MultipartFile
 */
public class MultipartFileMethodArgumentResolver extends AbstractCommonTypeMethodArgumentResolver {
    @Override
    protected boolean supportsInternal(Class<?> type) {
        return isFileType(type);
    }

    @Override
    protected Object resolveArgumentInternal(Class<?> type, Object parameterValue, MethodParameter methodParameter) throws Exception {
        // 压根没有上传文件时，parameterValue就是null，这个时候直接返回null即可
        if (parameterValue == null) {
            return null;
        }

        return handleSingleFile((Part) parameterValue, type);
    }

    @Override
    protected Object[] getSource(MethodParameter methodParameter, HttpServletRequest request) {
        Object[] source = null;
        try {
            List<Part> matchedParts = new ArrayList<>();
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals(methodParameter.getParameterName())) {
                    matchedParts.add(part);
                }
            }
            source = matchedParts.toArray();
        } catch (IOException | ServletException e) {
            // 这里不抛出异常，什么也不干，相当于返回null，针对的一种场景是：比如修改商品记录不牵涉到图片的修改，
            // 那么文件类型的参数就直接赋值为null即可，抛异常的话会中断控制器方法的执行
            // 没有上传文件时，调用request.getParts()方法是会抛异常的
        }
        return source;
    }

    protected boolean isFileType(Class<?> fileType) {
        return Part.class == fileType ||
                MultipartFile.class == fileType;
    }

    protected <T> T handleSingleFile(Part part, Class<T> paramType) {
        if (Part.class == paramType) {
            return (T) part;
        } else {
            return (T) new StandardMultipartFile(part, getFileName(part));
        }
    }

    protected String getFileName(Part part) {
        return part.getSubmittedFileName();
    }

}

