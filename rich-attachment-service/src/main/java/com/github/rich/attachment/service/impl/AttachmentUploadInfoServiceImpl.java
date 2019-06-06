package com.github.rich.attachment.service.impl;

import com.github.rich.attachment.entity.AttachmentUploadInfo;
import com.github.rich.attachment.mapper.AttachmentUploadInfoMapper;
import com.github.rich.attachment.service.IAttachmentUploadInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-06-06
 */
@Service
public class AttachmentUploadInfoServiceImpl extends ServiceImpl<AttachmentUploadInfoMapper, AttachmentUploadInfo> implements IAttachmentUploadInfoService {

    @Override
    public Boolean save(String fileId, String fileName, String md5, String filePath, Upload upload, String contentType, Long fileSize) {
        AttachmentUploadInfo uploadInfo = new AttachmentUploadInfo();
        uploadInfo.setId(fileId);
        uploadInfo.setMd5(md5);
        uploadInfo.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        uploadInfo.setCreateTime(LocalDateTime.now());
        uploadInfo.setFileName(fileName);
        uploadInfo.setFileType(contentType);
        uploadInfo.setSize(fileSize);
        uploadInfo.setStorageType(upload.getStorage().getValue());
        uploadInfo.setPath(filePath);
        return this.save(uploadInfo);
    }
}
