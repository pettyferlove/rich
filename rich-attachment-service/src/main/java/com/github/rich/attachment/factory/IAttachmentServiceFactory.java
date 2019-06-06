package com.github.rich.attachment.factory;

import com.github.rich.attachment.constants.StorageTypeEnum;
import com.github.rich.attachment.service.IAttachmentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Petty
 */
public interface IAttachmentServiceFactory extends ApplicationContextAware, InitializingBean {

    /**
     * 选择储存类型
     * @param type 枚举
     * @return 上传服务或下载服务
     */
    IAttachmentService create(StorageTypeEnum type);

}
