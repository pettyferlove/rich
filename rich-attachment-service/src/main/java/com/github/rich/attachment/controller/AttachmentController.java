package com.github.rich.attachment.controller;

import com.github.rich.attachment.service.IAttachmentOperaService;
import com.github.rich.attachment.vo.Upload;
import com.github.rich.attachment.vo.UploadResult;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @author Petty
 */
@Api(value = "附件操作", tags = {"附件操作接口"})
@Slf4j
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final IAttachmentOperaService attachmentOperaService;

    public AttachmentController(IAttachmentOperaService attachmentOperaService) {
        this.attachmentOperaService = attachmentOperaService;
    }

    @ApiOperation(value = "上传附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "upload", value = "upload", dataTypeClass = Upload.class),
            @ApiImplicitParam(paramType = "query", name = "file", value = "file", dataTypeClass = MultipartFile.class)
    })
    @PostMapping("upload")
    public R<UploadResult> upload(@Validated Upload upload, MultipartFile file) {
        return new R<>(attachmentOperaService.upload(upload, file));
    }

    @ApiOperation(value = "下载附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.download(id, response);
    }

    @ApiOperation(value = "查看附件（针对图片、PDF等文件）", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("view/{id}")
    public void view(@PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.view(id, response);
    }

    @ApiOperation(value = "删除附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id){
        return new R<>(attachmentOperaService.delete(id));
    }

    @ApiOperation(value = "批量删除附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @Deprecated
    @PostMapping("/batch/delete")
    public R<Boolean> deleteBatch(String[] ids){
        return new R<>(attachmentOperaService.deleteBatch(ids));
    }

}
