package com.github.rich.attachment.service;

import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Petty
 */
public interface IAttachmentOperaService {

    /**
     * 文件操作-文件上传
     * @param upload Upload
     * @param file MultipartFile
     * @return UploadResult
     */
    UploadResult upload(Upload upload, MultipartFile file);

    /**
     * 文件操作-文件下载
     * @param id 文件ID
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    void download(String id, HttpServletResponse response) throws Exception;

    /**
     * 文件操作-文件查看
     * @param id 文件ID
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    void view(String id, HttpServletResponse response) throws Exception;

}
