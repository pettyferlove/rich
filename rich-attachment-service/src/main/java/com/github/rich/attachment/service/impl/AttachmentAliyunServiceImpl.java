package com.github.rich.attachment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.github.rich.attachment.config.AttachmentAliyunProperties;
import com.github.rich.attachment.constants.FileTypeEnum;
import com.github.rich.attachment.constants.SecurityTypeEnum;
import com.github.rich.attachment.service.IAttachmentService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author Petty
 */
@Service("aliyun")
public class AttachmentAliyunServiceImpl implements IAttachmentService {

    private final AttachmentAliyunProperties aliyunProperties;

    private final OSS oss;

    private final String URL_STR = "https://%s.%s/%s";

    public AttachmentAliyunServiceImpl(AttachmentAliyunProperties aliyunProperties, OSS oss) {
        this.aliyunProperties = aliyunProperties;
        this.oss = oss;
    }

    @Override
    public UploadResult upload(Upload upload, MultipartFile file) {
        UploadResult result = new UploadResult();
        PutObjectResult putObjectResult = null;
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
            @NotNull SecurityTypeEnum security = upload.getSecurity();
            if(security ==SecurityTypeEnum.Private){
                meta.setObjectAcl(CannedAccessControlList.Private);
            } else if(security ==SecurityTypeEnum.PublicRead){
                result.setUrl(String.format(URL_STR,aliyunProperties.getBucket(),aliyunProperties.getEndpoint(),filePath.toString()));
                meta.setObjectAcl(CannedAccessControlList.PublicRead);
            }
            putObjectResult = oss.putObject(aliyunProperties.getBucket(), filePath.toString(), file.getInputStream(),meta);
            result.setMd5(md5);
            result.setFileId(fileId);
            result.setStoreType(upload.getStorage().getValue());
            System.out.println(upload.getName());
            System.out.println(upload.getStorage().getValue());
            System.out.println(filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(putObjectResult);
        return result;
    }

    @Override
    public void download() {

    }
}
