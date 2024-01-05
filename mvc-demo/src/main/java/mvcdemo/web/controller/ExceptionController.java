package mvcdemo.web.controller;

import com.zl.mvc.mapping.RequestMapping;
import com.zl.mvc.view.JsonViewResult;

public class ExceptionController {
    @RequestMapping("/ex")
    public JsonViewResult suan(int a,int b){
        int result = a/b;
        return new JsonViewResult(new ResponseVO(200,"ok",result));
    }
}
