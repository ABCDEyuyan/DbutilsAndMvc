package mvcdemo.web.controller;

import com.zl.mvc.exception.ExceptionHandler;
import com.zl.mvc.view.JsonViewResult;

public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public JsonViewResult handleRuntime(RuntimeException re){
        System.out.println("re = " + re);
        return new JsonViewResult(new ResponseVO(10001,"订单超时。。","runtime--"));
    }

    @ExceptionHandler(ArithmeticException.class)
    public JsonViewResult handleArithmeticException(ArithmeticException re){
        System.out.println("suan shu re = " + re);
        return new JsonViewResult(new ResponseVO(10002,"sdfa","算数--"));
    }


}
