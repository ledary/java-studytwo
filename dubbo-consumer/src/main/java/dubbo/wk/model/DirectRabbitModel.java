package dubbo.wk.model;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.Serializable;

/**
 * Created by wgp on 2018/6/23.
 */
public class DirectRabbitModel extends BaseModel{
    private Long id;
    private String uuid;
    private String dislike;
    private String favorite;

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

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



    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
