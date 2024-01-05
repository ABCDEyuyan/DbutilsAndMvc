package com.nf.demo.web.controller;

import com.nf.demo.vo.ResponseVO;
import com.zl.mvc.ViewResult;
import com.zl.mvc.exception.ExceptionHandler;

import static com.zl.mvc.handler.HandlerHelper.json;

public class GlboalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ViewResult handleRuntime(RuntimeException re){
        return json(new ResponseVO(500, "cuowu", null));
    }
}
