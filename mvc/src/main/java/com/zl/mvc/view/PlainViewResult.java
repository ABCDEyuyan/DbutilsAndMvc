package com.zl.mvc.view;

import com.zl.mvc.ViewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlainViewResult extends ViewResult {
    private String text;

    public PlainViewResult(String text) {
        this.text = text;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().print(text);
    }
}
