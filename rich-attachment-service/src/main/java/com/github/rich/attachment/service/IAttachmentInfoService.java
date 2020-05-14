package com.github.rich.attachment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.domain.vo.Upload;

/**
 * <p>
 * 附件信息保存实体，持久化到数据库
 * 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-06-06
 */
public interface IAttachmentInfoService extends IService<AttachmentInfo> {

    /**
     * 保存文件上传记录
     *
     * @param userId      UserId
     * @param fileId      FileID
     * @param fileName    FileName
     * @param md5         MD5
     * @param filePath    文件相对地址
     * @param upload      上传对象
     * @param contentType 类型
     * @param fileSize    大小
     * @return Boolean
     */
    Boolean save(String userId, String fileId, String fileName, String md5, String filePath, Upload upload, String contentType, Long fileSize);


    /**
     * 通过附件ID获取附件内容
     *
     * @param id 附件ID
     * @return AttachmentInfo
     */
    AttachmentInfo getAttachmentInfoById(String id);


}
