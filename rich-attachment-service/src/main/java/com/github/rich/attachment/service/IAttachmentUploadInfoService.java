package com.github.rich.attachment.service;

import com.github.rich.attachment.constants.StorageTypeEnum;
import com.github.rich.attachment.entity.AttachmentUploadInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.attachment.vo.Upload;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-06-06
 */
public interface IAttachmentUploadInfoService extends IService<AttachmentUploadInfo> {


    /**
     * 保存文件上传记录
     * @param fileId FileID
     * @param fileName FileName
     * @param md5 MD5
     * @param filePath 文件相对地址
     * @param upload 上传对象
     * @param contentType 类型
     * @param fileSize 大小
     * @return Boolean
     */
    Boolean save(String fileId,String fileName,String md5, String filePath, Upload upload, String contentType, Long fileSize);

}
