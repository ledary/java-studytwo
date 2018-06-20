package dubbo.wk.model;

import dubbo.wk.utils.excel.ExcelDesc;

/**
 * Created by wgp on 2018/6/20.
 */
public class ExcelModel {

//    备案号	合同协议号	企业内部编号	报关单号	统一编号	申报地海关	贸易方式	进出口口岸	放行日期
//    进出口日期	申报日期	运输方式	运输工具名称	境内目的地	启运国(地区)/运抵国(地区)	装货港/指运港
//    贸易国（地区）	包装种类	件数	总净重	总毛重	成交方式
//    运费类型	运费值	运费币制	保费类型	保费值	保费币制	杂费类型	杂费值	杂费币制	报关单类型
    @ExcelDesc(value = "备案号",orderBy = "8")
    private String recordNumber;
    @ExcelDesc(value = "合同协议号",orderBy = "1")
    private String agreementNumber;
    @ExcelDesc(value = "企业内部编号",orderBy = "2")
    private String enterpriseNumber;
    @ExcelDesc(value = "报关单号",orderBy = "3")
    private String customsNumber;
    @ExcelDesc(value = "贸易方式",orderBy = "4")
    private String tradeWay;
    @ExcelDesc(value = "通过日期",orderBy = "5")
    private String passDate;
    @ExcelDesc(value = "申报日期",orderBy = "6")
    private String declareDate;
    @ExcelDesc(value = "运输方式",orderBy = "7")
    private String transportWay;

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getEnterpriseNumber() {
        return enterpriseNumber;
    }

    public void setEnterpriseNumber(String enterpriseNumber) {
        this.enterpriseNumber = enterpriseNumber;
    }

    public String getCustomsNumber() {
        return customsNumber;
    }

    public void setCustomsNumber(String customsNumber) {
        this.customsNumber = customsNumber;
    }

    public String getTradeWay() {
        return tradeWay;
    }

    public void setTradeWay(String tradeWay) {
        this.tradeWay = tradeWay;
    }

    public String getPassDate() {
        return passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate;
    }

    public String getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(String declareDate) {
        this.declareDate = declareDate;
    }

    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }
}
