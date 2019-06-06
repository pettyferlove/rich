package com.github.rich.attachment.service;

import com.github.rich.attachment.dto.Download;
import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

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
     * @param attachmentInfo  文件信息
     * @param outputStream  输出流
     */
    void download(AttachmentInfo attachmentInfo, OutputStream outputStream);

}
