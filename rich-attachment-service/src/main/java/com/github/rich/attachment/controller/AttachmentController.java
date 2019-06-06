package com.github.rich.attachment.controller;

import com.github.rich.attachment.factory.IAttachmentServiceFactory;
import com.github.rich.attachment.service.IAttachmentService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.common.core.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @author Petty
 */
@Slf4j
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final IAttachmentServiceFactory factory;

    public AttachmentController(IAttachmentServiceFactory factory) {
        this.factory = factory;
    }

    @PostMapping("upload")
    public R<UploadResult> upload(@Validated Upload upload, MultipartFile file){
        IAttachmentService attachmentService = factory.create(upload.getStorage());
        return new R<>(attachmentService.upload(upload, file));
    }

    @GetMapping("download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response){

    }


}
