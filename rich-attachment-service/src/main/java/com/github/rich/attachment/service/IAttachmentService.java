package com.github.rich.attachment.service;

import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Petty
 */
public interface IAttachmentService {

    /**
     * 文件上传
     * @param file 文件对象
     * @param upload 上传文件基本信息
     * @return UploadResult
     */
    UploadResult upload(Upload upload, MultipartFile file);

    /**
     * 下载
     */
    void download();

}