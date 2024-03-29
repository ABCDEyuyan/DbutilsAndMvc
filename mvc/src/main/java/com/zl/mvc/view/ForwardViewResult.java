package com.zl.mvc.view;

import com.zl.mvc.ViewResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ForwardViewResult extends ViewResult {
    private String url;
    private Map<String,Object> model;

    public ForwardViewResult(String url) {
        this(url, new HashMap<>());
    }

    public ForwardViewResult(String url, Map<String, Object> model) {
        this.url = url;
        this.model = model;

    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.getRequestDispatcher(url).forward(req,resp);
    }

    private void initModel(HttpServletRequest req){
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            req.setAttribute(entry.getKey(),entry.getValue());
        }
    }
}
