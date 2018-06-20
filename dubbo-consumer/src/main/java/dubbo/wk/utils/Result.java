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

    public String getCode() {
        return code;
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

    private String message;
    private T obj;

    public static Result setTrue(){
        return new Result(Constant.FAIL_CODE);
    }
    public static Result setFalse(){
        return new Result(Constant.SUCCESS_CODE);
    }


    public Result(String code,String message,T t){
        this.code = code;
        this.message = message;
        this.obj = t;
    }
    public Result(String code,String message){
        this.code = code;
        this.message = message;
    }

    public Result(String code,T t){
        this.code = code;
        this.obj = t;
    }

    public Result(String code){
        this.code = code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
