package dubbo.wk.controller;

import dubbo.wk.constants.Constant;
import dubbo.wk.service.MessageQueueService;
import dubbo.wk.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description 消息 队列发送 模拟类
 * Created by wgp on 2018/6/23.
 */

@RequestMapping("message")
@Controller
public class MessageQueueController {
    private Logger logger = LoggerFactory.getLogger(MessageQueueController.class);
    @Autowired
    @Qualifier("rabbitServiceImpl")
    private MessageQueueService messageQueueService;

    @Autowired
    @Qualifier("delayServiceImpl")
    private MessageQueueService messageDelayQueueService;

    @RequestMapping(value = "sendRabbitMessage",method = RequestMethod.GET)
    @ResponseBody
    public Result sendMessage(){
        Result result = new Result();
        result.setCode(Constant.FAIL_CODE);
        result.setMessage("啊哦，失败了呦");
        result.setObj(false);
        try{
            result.setCode(Constant.SUCCESS_CODE);
            result.setMessage("耶，成功了");
            result.setObj(true);
            messageQueueService.sendDirectRabbitMessage();
        }catch (Exception e){
            logger.error("发送消息异常",e);
        }finally {
            return result;
        }

    }


    @RequestMapping(value = "sendDelayMessage",method = RequestMethod.GET)
    @ResponseBody
    public Result sendDelayMessage(){
        Result result = new Result();
        result.setCode(Constant.FAIL_CODE);
        result.setMessage("啊哦，失败了呦");
        result.setObj(false);
        try{

            messageDelayQueueService.sendDirectRabbitMessage();
            result.setCode(Constant.SUCCESS_CODE);
            result.setMessage("耶，成功了");
            result.setObj(true);
        }catch (Exception e){
            logger.error("发送消息异常",e);
        }finally {
            return result;
        }

    }
}
