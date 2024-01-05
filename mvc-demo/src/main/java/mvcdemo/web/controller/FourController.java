package mvcdemo.web.controller;


import com.zl.mvc.ViewResult;
import com.zl.mvc.view.ForwardViewResult;
import com.zl.mvc.view.JsonViewResult;
import mvcdemo.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zl.mvc.handler.HandlerHelper.json;

public class FourController {

    public ViewResult process() throws ServletException, IOException {
        System.out.println("4444-----------");
        //return new ForwardViewResult("/index.jsp");



        Student student = new Student(100, "abc");
       // return new JsonViewResult(student);
      //  return json(student);

        return json(student);
    }
}
