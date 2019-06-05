package com.github.rich.attachment.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.github.rich.attachment.config.AttachmentAliyunProperties;
import com.github.rich.attachment.service.IAttachmentUploadService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Petty
 */
@Service
public class AttachmentUploadAliyunServiceImpl implements IAttachmentUploadService {

    private final AttachmentAliyunProperties aliyunProperties;

    private final OSS oss;

    public AttachmentUploadAliyunServiceImpl(AttachmentAliyunProperties aliyunProperties, OSS oss) {
        this.aliyunProperties = aliyunProperties;
        this.oss = oss;
    }

    @Override
    public UploadResult upload(Upload upload, MultipartFile file) {
        PutObjectResult putObjectResult = null;
        try {
            ObjectMetadata meta = new ObjectMetadata();
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(file.getBytes()));
            meta.setContentMD5(md5);
            meta.setObjectAcl(CannedAccessControlList.PublicRead);
            putObjectResult = oss.putObject(aliyunProperties.getBucket(), upload.getName(), file.getInputStream(),meta);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(putObjectResult);
        return null;
    }
}
