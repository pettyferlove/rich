package com.github.rich.attachment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.github.rich.attachment.config.AttachmentAliyunProperties;
import com.github.rich.attachment.constants.FileTypeEnum;
import com.github.rich.attachment.constants.SecurityTypeEnum;
import com.github.rich.attachment.dto.Download;
import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.service.IAttachmentInfoService;
import com.github.rich.attachment.service.IAttachmentService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.common.core.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Slf4j
@Service("aliyun")
public class AttachmentAliyunServiceImpl implements IAttachmentService {

    private final IAttachmentInfoService attachmentInfoService;

    private final AttachmentAliyunProperties aliyunProperties;

    private final OSS oss;

    private final String URL_STR = "https://%s.%s/%s";

    public AttachmentAliyunServiceImpl(IAttachmentInfoService attachmentInfoService, AttachmentAliyunProperties aliyunProperties, OSS oss) {
        this.attachmentInfoService = attachmentInfoService;
        this.aliyunProperties = aliyunProperties;
        this.oss = oss;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult upload(Upload upload, MultipartFile file) {
        Assert.notNull(file, "上传文件不可为空");
        UploadResult result = new UploadResult();
        String fileId = IdUtil.simpleUUID();
        FileTypeEnum parse = FileTypeEnum.parse(file.getContentType());
        StringBuilder filePath = new StringBuilder();
        filePath.append(aliyunProperties.getRoot());
        filePath.append("/");
        filePath.append(upload.getGroup());
        filePath.append("/");
        filePath.append(fileId);
        filePath.append(parse.getExpansionName());
        try {
            ObjectMetadata meta = new ObjectMetadata();
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(file.getBytes()));
            meta.setContentMD5(md5);
            if (attachmentInfoService.save(fileId, file.getOriginalFilename(), md5, filePath.toString(), upload, file.getContentType(), file.getSize())) {
                result.setMd5(md5);
                result.setFileId(fileId);
                result.setStoreType(upload.getStorage().getValue());
                @NotNull SecurityTypeEnum security = upload.getSecurity();
                if (security == SecurityTypeEnum.Private) {
                    meta.setObjectAcl(CannedAccessControlList.Private);
                } else if (security == SecurityTypeEnum.PublicRead) {
                    result.setUrl(String.format(URL_STR, aliyunProperties.getBucket(), aliyunProperties.getEndpoint(), filePath.toString()));
                    meta.setObjectAcl(CannedAccessControlList.PublicRead);
                }
                oss.putObject(aliyunProperties.getBucket(), filePath.toString(), file.getInputStream(), meta);
                result.setMd5(md5);
                result.setFileId(fileId);
                result.setStoreType(upload.getStorage().getValue());
            } else {
                log.error("file upload info save error -->params:{},{}", upload, file);
                throw new BaseRuntimeException("file upload info save error");
            }
        } catch (IOException e) {
            log.error("file upload error -->params:{},{}", upload, file);
            throw new BaseRuntimeException("file upload error");
        }
        return result;
    }

    @Override
    public void download(AttachmentInfo attachmentInfo, OutputStream outputStream) {
        try {
            OSSObject ossObject = oss.getObject(aliyunProperties.getBucket(), attachmentInfo.getPath());
            int ch;
            while ((ch = ossObject.getObjectContent().read()) != -1) {
                outputStream.write(ch);
            }
        } catch (Exception e) {
            log.error("file download error -->params:{}", attachmentInfo);
            throw new BaseRuntimeException("file download error");
        }
    }

    @Override
    public Boolean delete(AttachmentInfo attachmentInfo) {
        try {
            oss.deleteObject(aliyunProperties.getBucket(),attachmentInfo.getPath());
            return true;
        } catch (Exception e){
            log.error("file delete error -->params:{}", attachmentInfo);
            throw new BaseRuntimeException("file delete error");
        }
    }
}
