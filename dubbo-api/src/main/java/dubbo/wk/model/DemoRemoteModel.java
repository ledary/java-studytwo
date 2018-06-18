package dubbo.wk.model;

import java.io.Serializable;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/22
 **/
public class DemoRemoteModel implements Serializable {
    private Long id;
    private String uuid;
    private String userName;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
