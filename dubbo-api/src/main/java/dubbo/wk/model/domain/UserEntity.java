package dubbo.wk.model.domain;

import dubbo.wk.model.domain.enmus.SexEnums;

import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public class UserEntity extends  BaseEntity {
    private String userName;
    private String password;
    private String phone;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String email;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
