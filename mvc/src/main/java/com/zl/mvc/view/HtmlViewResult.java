package com.zl.mvc.view;

import com.zl.mvc.ViewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlViewResult extends ViewResult {
    private String html;

    public HtmlViewResult(String html) {
        this.html = html;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().print(html);
    }
}
