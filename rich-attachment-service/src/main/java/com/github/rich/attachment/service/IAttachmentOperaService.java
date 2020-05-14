package com.github.rich.attachment.service;

import com.github.rich.attachment.domain.vo.Upload;
import com.github.rich.attachment.domain.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 附件操作
 * @author Petty
 */
public interface IAttachmentOperaService {

    /**
     * 文件操作-文件上传
     *
     * @param userId userId
     * @param upload Upload
     * @param file   MultipartFile
     * @return UploadResult
     */
    UploadResult upload(String userId, Upload upload, MultipartFile file);

    /**
     * 文件操作-文件下载
     *
     * @param id       文件ID
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    void download(String id, HttpServletResponse response) throws Exception;

    /**
     * 文件操作-文件查看
     *
     * @param id       文件ID
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    void view(String id, HttpServletResponse response) throws Exception;

    /**
     * 文件操作-删除文件
     *
     * @param id 文件ID
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 文件操作-批量删除文件
     *
     * @param ids 文件ID
     * @return Boolean
     */
    Boolean deleteBatch(String[] ids);
}
