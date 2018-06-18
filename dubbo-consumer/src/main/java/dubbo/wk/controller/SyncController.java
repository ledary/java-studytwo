package dubbo.wk.controller;

import dubbo.wk.service.AsyncService;
import dubbo.wk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Future;

/**
 * @author WGP
 * @description 异步发送消息Controller
 * @date 2018/6/15
 **/

@RequestMapping("/async")
@Controller
public class SyncController {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/syncSendEmail")
    @ResponseBody
    public Result<Boolean> asyncSendEmail() {
        Future<Boolean> future = asyncService.asyncSendEmail();
        Result<Boolean> result = Result.setTrue();
        result.setObj(true);

        try {
            ///这里使用了循环判断，等待获取结果信息
//            while (true) {
//                //判断是否执行完毕
//                if (future.isDone()) {
//                    Boolean flag = future.get();
//                    result.setObj(flag);
//                    break;
//                }
//            }

//            直接使用get方法获取返回值
            result.setObj(future.get());
        } catch (Exception e) {

        } finally {
            return result;
        }
    }

    /*
    无返回值的异步方法
     */
    @RequestMapping("/voidSendEmail")
    @ResponseBody
    public Result<Boolean> syncVoidSendEmail() {
        asyncService.voidSendEmail();
        System.out.println("异步方法执行后的执行内容");
        Result<Boolean> result = Result.setTrue();
        result.setObj(true);
        return result;
    }
}
