package com.github.rich.attachment.service;

import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.domain.vo.Upload;
import com.github.rich.attachment.domain.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * 附件核心服务，对接具体的存储方式，比如本地文件操作 阿里云附件操作
 * @author Petty
 */
public interface IAttachmentService {

    /**
     * 文件上传
     *
     * @param userId userId
     * @param file   文件对象
     * @param upload 上传文件基本信息
     * @return UploadResult
     */
    UploadResult upload(String userId, Upload upload, MultipartFile file);

    /**
     * 下载
     *
     * @param attachmentInfo 文件信息
     * @param outputStream   输出流
     */
    void download(AttachmentInfo attachmentInfo, OutputStream outputStream);


    /**
     * 删除文件（同时删除数据记录）
     *
     * @param attachmentInfo 文件信息
     * @return Boolean
     */
    Boolean delete(AttachmentInfo attachmentInfo);

}
