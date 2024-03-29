package com.zl.mvc.view;

import com.zl.mvc.ViewResult;
import com.zl.mvc.util.ObjectUtils;
import com.zl.mvc.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类主要是用来响应流这种视图结果的
 * @see FileViewResult
 * @see ViewResult
 * @see com.zl.mvc.handler.HandlerHelper
 */
public class StreamViewResult extends ViewResult {
    private  Map<String, String> headers ;
    private InputStream inputStream;

    public StreamViewResult(InputStream inputStream) {
        this(inputStream, new HashMap<>());
    }

    public StreamViewResult(InputStream inputStream, Map<String, String> headers) {
        this.inputStream = inputStream;
        this.headers = headers;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        writeContentType(resp);
        writeHeaders(resp);
        writeContent(resp);
    }

    protected void writeContentType(HttpServletResponse resp) throws Exception{
        resp.setContentType(StreamUtils.APPLICATION_OCTET_STREAM_VALUE);
    }

    protected void writeHeaders(HttpServletResponse resp) throws Exception {
        if (ObjectUtils.isEmpty(headers)) {
            return;
        }
        headers.forEach((k,v)-> resp.setHeader(k,v));
    }

    protected void writeContent(HttpServletResponse resp) throws Exception {
        try (InputStream input = this.inputStream; OutputStream output = resp.getOutputStream()) {
            StreamUtils.copy(input, output);
        }
    }

}
