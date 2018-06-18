package dubbo.wk.model.domain.enmus;

/**
 * @author WGP
 * @descriptionparent
 * @date 2018/4/30
 **/
public enum SexEnums {

    MALE((byte) 0, "男"),
    FEMALE((byte) 1, "女");

    SexEnums(Byte code, String zhName) {
        this.code = code;
        this.zhName = zhName;
    }

    private Byte code;
    private String zhName;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public static SexEnums getSex(Byte code){
        if(code == null){
            return null;
        }
        for (SexEnums sex : SexEnums.values()) {
            if(sex.getCode().equals(code)){
                return sex;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.getZhName();
    }
}
