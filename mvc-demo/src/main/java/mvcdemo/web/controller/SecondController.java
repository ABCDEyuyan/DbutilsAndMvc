package mvcdemo.web.controller;

import com.zl.mvc.HttpRequestHandler;
import com.zl.mvc.ViewResult;
import com.zl.mvc.view.ForwardViewResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EmptyStackException;

public class SecondController implements HttpRequestHandler {
    @Override
    public void processRequest(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        //req.getParameter("asdf");
    //表单提交时，有多个控件名字一样
        String[] asdfs = req.getParameterValues("asdf");


        System.out.println("2222------");
        response.getWriter().println("second ----");

    }
}
