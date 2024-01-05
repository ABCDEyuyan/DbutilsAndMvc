package mvcdemo.web.controller;

import com.zl.mvc.ViewResult;
import com.zl.mvc.mapping.RequestMapping;

import static com.zl.mvc.handler.HandlerHelper.json;


public class ProductController3 {

    @RequestMapping("/m1in3")
    public void m1(){
        System.out.println("m1 in proudct------");
    }

    @RequestMapping("/m2")
    public ViewResult m2(){
        return json(new ResponseVO(200,"ok","asdf"));
    }
}
