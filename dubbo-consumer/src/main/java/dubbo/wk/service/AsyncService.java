package dubbo.wk.service;

import java.util.concurrent.Future;

/**
 * @author WGP
 * @description  异步方法接口类
 * @date 2018/6/15
 **/
public interface AsyncService {
    Future<Boolean> asyncSendEmail();
    void  voidSendEmail();
}
