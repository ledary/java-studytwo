package dubbo.wk.service.impl;

import dubbo.wk.model.ExcelTemplateModel;
import dubbo.wk.model.WordTemplateModel;
import dubbo.wk.service.FileService;
import org.springframework.stereotype.Service;

/**
 * Created by wgp on 2018/6/21.
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public ExcelTemplateModel queryExcelTemplate() {
       ExcelTemplateModel model = new ExcelTemplateModel();
       model.setDeliveryNumber("0102020193");
       model.setDeclareDate("2018-02-03");
       model.setExportDate("2018-03-02");
       model.setForwardingUnit("长江公司");
       model.setNetWeight("25.365");
       model.setPackageKind("纸箱");
       model.setPackageNumber("19283883");
       model.setManageUnit("大合公司");
       model.setManageUnitNumber("039398928");
       model.setSumPrice("108.3");
       model.setDeliveryNumber("0938837");
       model.setPortOfExport("泉州");
       return  model;
    }

   @Override
   public WordTemplateModel queryWordTemplate() {
      WordTemplateModel model = new WordTemplateModel();
      model.setApplyPerson("武刚鹏");
      model.setApprovePerson("武刚鹏");
      model.setName("武刚鹏");
      model.setPassDate("2018-01-01");
      model.setQuantity("100.032");
      model.setWeight("290.3");
      return model;
   }
}
