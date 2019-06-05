package com.github.rich.attachment.controller;

import com.github.rich.attachment.service.IAttachmentUploadService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.common.core.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Petty
 */
@Slf4j
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final IAttachmentUploadService attachmentUploadService;

    public AttachmentController(IAttachmentUploadService attachmentUploadService) {
        this.attachmentUploadService = attachmentUploadService;
    }

    @PostMapping("upload")
    public R<UploadResult> upload(@Validated Upload upload, MultipartFile file){
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        attachmentUploadService.upload(upload, file);
        return null;
    }
}
