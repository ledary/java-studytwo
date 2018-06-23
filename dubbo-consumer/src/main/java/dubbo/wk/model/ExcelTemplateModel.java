package dubbo.wk.model;

import dubbo.wk.utils.excel.ExcelDesc;
import dubbo.wk.utils.excel.ExcelTemplateDesc;

/**
 * Created by wgp on 2018/6/21.
 */
public class ExcelTemplateModel extends BaseModel {
    //出口口岸
    @ExcelDesc(value = "portOfExport")
    private String portOfExport;
    //备案号
    @ExcelDesc(value = "recordNumber")
    private String recordNumber;
    //申报日期
    @ExcelDesc(value = "declareDate")
    private String declareDate;
    //出口日期
    @ExcelDesc(value = "exportDate")
    private String exportDate;
    //经营单位号
    @ExcelDesc(value = "manageUnitNumber")
    private String manageUnitNumber;
    //交通工具名称
    @ExcelDesc(value = "transportName")
    private String transportName;
    //运输方式
    @ExcelDesc(value = "transportWay")
    private String transportWay;
    //发货单位号
    @ExcelDesc(value = "forwardingUnitNumber")
    private String forwardingUnitNumber;
    //经营单位号
    @ExcelDesc(value = "deliveryNumber")
    private String deliveryNumber;
    //发货单位
    @ExcelDesc(value = "forwardingUnit")
    private String forwardingUnit;
    //毛重
    @ExcelDesc(value = "roughWeight")
    private String roughWeight;
    //经营单位
    @ExcelDesc(value = "manageUnit")
    private String manageUnit;
    //交易方式
    @ExcelDesc(value = "tradeWay")
    private String tradeWay;
    //包装种类
    @ExcelDesc(value = "packageKind")
    private String packageKind;
    //提运单号
    @ExcelDesc(value = "packageNumber")
    private String packageNumber;
    //总价
    @ExcelDesc(value = "sumPrice")
    private String sumPrice;
    //净重
    @ExcelDesc(value = "netWeight")
    private String netWeight;
    //单价
    @ExcelDesc(value = "unitPrice")
    private String unitPrice;





    public String getPortOfExport() {
        return portOfExport;
    }
    public void setPortOfExport(String portOfExport) {
        this.portOfExport = portOfExport;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getDeclareDate() {
        return declareDate;
    }

    public void setDeclareDate(String declareDate) {
        this.declareDate = declareDate;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getManageUnitNumber() {
        return manageUnitNumber;
    }

    public void setManageUnitNumber(String manageUnitNumber) {
        this.manageUnitNumber = manageUnitNumber;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }

    public String getForwardingUnitNumber() {
        return forwardingUnitNumber;
    }

    public void setForwardingUnitNumber(String forwardingUnitNumber) {
        this.forwardingUnitNumber = forwardingUnitNumber;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getForwardingUnit() {
        return forwardingUnit;
    }

    public void setForwardingUnit(String forwardingUnit) {
        this.forwardingUnit = forwardingUnit;
    }

    public String getRoughWeight() {
        return roughWeight;
    }

    public void setRoughWeight(String roughWeight) {
        this.roughWeight = roughWeight;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

    public String getTradeWay() {
        return tradeWay;
    }

    public void setTradeWay(String tradeWay) {
        this.tradeWay = tradeWay;
    }

    public String getPackageKind() {
        return packageKind;
    }

    public void setPackageKind(String packageKind) {
        this.packageKind = packageKind;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
