package com.github.rich.attachment.service.impl;

import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.mapper.AttachmentInfoMapper;
import com.github.rich.attachment.service.IAttachmentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class AttachmentInfoServiceImpl extends ServiceImpl<AttachmentInfoMapper, AttachmentInfo> implements IAttachmentInfoService {

    @Override
    public Boolean save(String fileId, String fileName, String md5, String filePath, Upload upload, String contentType, Long fileSize) {
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setId(fileId);
        attachmentInfo.setMd5(md5);
        attachmentInfo.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        attachmentInfo.setCreateTime(LocalDateTime.now());
        attachmentInfo.setFileName(fileName);
        attachmentInfo.setFileType(contentType);
        attachmentInfo.setSize(fileSize);
        attachmentInfo.setStorageType(upload.getStorage().getValue());
        attachmentInfo.setPath(filePath);
        return this.save(attachmentInfo);
    }

    @Override
    public AttachmentInfo getAttachmentInfoById(String id) {
        return this.getById(id);
    }
}
