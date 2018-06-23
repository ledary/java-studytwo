package dubbo.wk.model;

/**
 * Created by wgp on 2018/6/22.
 */
public class PdfTemplateModel extends BaseModel {
    private String name;
    private Integer age;
    private String goal;
    private String plan;
    private String createDate;
    private String endnode;
    private String dutyPerson;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndnode() {
        return endnode;
    }

    public void setEndnode(String endnode) {
        this.endnode = endnode;
    }

    public String getDutyPerson() {
        return dutyPerson;
    }

    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }
}
