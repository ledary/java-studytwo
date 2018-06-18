package dubbo.wk.model.domain;

import java.util.Date;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public class BaseEntity {
    protected  String uuid;
    protected Long id;
    private Date created;
    private Date updated;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
