package dubbo.wk.utils;

import dubbo.wk.constants.Constant;

import java.io.Serializable;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/6/15
 **/
public class Result<T> implements Serializable{

    private String code;


    private String message;
    private T obj;

       ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
