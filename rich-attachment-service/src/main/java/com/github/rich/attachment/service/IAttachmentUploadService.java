package com.github.rich.attachment.service;

import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 附件上传
 * @author Petty
 */
public interface IAttachmentUploadService {

    /**
     * 文件上传
     * @param file 文件对象
     * @param upload 上传文件基本信息
     * @return UploadResult
     */
    UploadResult upload(Upload upload, MultipartFile file);

}
