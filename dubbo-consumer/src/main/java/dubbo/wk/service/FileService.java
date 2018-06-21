package dubbo.wk.service;

import dubbo.wk.model.ExcelTemplateModel;
import dubbo.wk.model.WordTemplateModel;

/**
 * Created by wgp on 2018/6/21.
 */
public interface FileService {
    ExcelTemplateModel queryExcelTemplate();
    WordTemplateModel queryWordTemplate();
}
