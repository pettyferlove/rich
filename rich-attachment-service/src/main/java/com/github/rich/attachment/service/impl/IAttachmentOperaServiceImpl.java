package com.github.rich.attachment.service.impl;

import com.github.rich.attachment.constants.FileType;
import com.github.rich.attachment.constants.StorageType;
import com.github.rich.attachment.entity.AttachmentInfo;
import com.github.rich.attachment.factory.IAttachmentServiceFactory;
import com.github.rich.attachment.service.IAttachmentInfoService;
import com.github.rich.attachment.service.IAttachmentOperaService;
import com.github.rich.attachment.service.IAttachmentService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.common.core.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Petty
 */
@Slf4j
@Service
public class IAttachmentOperaServiceImpl implements IAttachmentOperaService {

    private final IAttachmentServiceFactory factory;

    private final IAttachmentInfoService attachmentInfoService;

    public IAttachmentOperaServiceImpl(IAttachmentServiceFactory factory, IAttachmentInfoService attachmentInfoService) {
        this.factory = factory;
        this.attachmentInfoService = attachmentInfoService;
    }


    @Override
    public UploadResult upload(String userId, Upload upload, MultipartFile file) {
        IAttachmentService attachmentService = factory.create(upload.getStorage());
        return attachmentService.upload(userId, upload, file);
    }

    @Override
    public void download(String id, HttpServletResponse response) throws Exception {
        Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
        if (attachmentInfoOptional.isPresent()) {
            AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
            IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
            attachmentService.download(attachmentInfo, response.getOutputStream());
            response.setCharacterEncoding("utf-8");
            response.setContentType(attachmentInfo.getFileType());
            String fileName = null;
            try {
                fileName = URLEncoder.encode(attachmentInfo.getFileName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        } else {
            throw new BaseRuntimeException("not found file", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void view(String id, HttpServletResponse response) throws Exception {
        Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
        if (attachmentInfoOptional.isPresent()) {
            AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
            String fileType = attachmentInfo.getFileType();
            if (FileType.IMAGE_JPEG == FileType.parse(fileType) || FileType.IMAGE_JPG == FileType.parse(fileType)) {
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                response.setCharacterEncoding("utf-8");
                response.setContentType(attachmentInfo.getFileType());
                attachmentService.download(attachmentInfo, response.getOutputStream());
            } else {
                throw new BaseRuntimeException("not support view this file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new BaseRuntimeException("not found file", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            boolean result = false;
            Optional<AttachmentInfo> attachmentInfoOptional = Optional.ofNullable(attachmentInfoService.getAttachmentInfoById(id));
            if (attachmentInfoOptional.isPresent()) {
                AttachmentInfo attachmentInfo = attachmentInfoOptional.get();
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                if (attachmentService.delete(attachmentInfo)) {
                    result = attachmentInfoService.removeById(attachmentInfo.getId());
                }

            }
            return result;
        } catch (Exception e) {
            throw new BaseRuntimeException("delete file error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteBatch(String[] ids) {
        try {
            boolean result = false;
            List<String> fileIds = Arrays.asList(ids);
            List<AttachmentInfo> attachmentInfos = (List<AttachmentInfo>) attachmentInfoService.listByIds(fileIds);
            for (AttachmentInfo attachmentInfo : attachmentInfos) {
                IAttachmentService attachmentService = factory.create(StorageType.parse(attachmentInfo.getStorageType()));
                if (attachmentService.delete(attachmentInfo)) {
                    result = attachmentInfoService.removeById(attachmentInfo.getId());
                }
            }
            return result;
        } catch (Exception e) {
            throw new BaseRuntimeException("batch delete file error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
