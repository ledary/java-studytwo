dubbo-consumer 工程下：

spring 集合 velocity异步发送邮件：

    发送邮件工具类EmailUtils
    veloctiyUtils veocity模板引擎工具类
    EmailModel 发送邮件实体类
    DataFormatUitls  格式化时间工具类
    Result  返回前台实体类

job 包下为 quartz定时器任务类

    集群和单机版配置
    集群配置需要建立自己的QuartzJobBean——MyDetailQuartzJobBean，采用反射方式调用对象方法
    其中集群配置的quartz表采用ASSCII编码，
    采用utf8b4b会提示索引长度过长
    
scheduler 包下为spring定时器任务类

    简单定时任务首先
utils工具包

    fileController  提供下载测试方法
    Excel导入，导出，根据模板导出工具方法
    利用velocity根据模板导出word,excel
    Pdf工具类根据模板导出PDF
    config包下  spring的解析占位符的工具类


