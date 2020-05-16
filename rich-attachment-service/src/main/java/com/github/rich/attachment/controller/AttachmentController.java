package com.github.rich.attachment.controller;

import com.github.rich.attachment.service.IAttachmentOperaService;
import com.github.rich.attachment.domain.vo.Upload;
import com.github.rich.attachment.domain.vo.UploadResult;
import com.github.rich.common.core.domain.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


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
    @UserLog(type = OperateType.ATTACH_UPLOAD, description = "用户附件上传")
    public R<UploadResult> upload(@Validated Upload upload, MultipartFile file) {
        return new R<>(attachmentOperaService.upload(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), upload, file));
    }

    @ApiOperation(value = "下载附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("download/{id}")
    @UserLog(type = OperateType.ATTACH_DOWNLOAD, description = "用户附件下载")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.download(id, response);
    }

    @ApiOperation(value = "查看附件（针对图片、PDF等文件）", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("view/{id}")
    @UserLog(type = OperateType.ATTACH_VIEW, description = "用户文件查看")
    public void view(@PathVariable String id, HttpServletResponse response) throws Exception {
        attachmentOperaService.view(id, response);
    }

    @ApiOperation(value = "删除附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.ATTACH_DELETE, description = "用户文件删除")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(attachmentOperaService.delete(id));
    }

    @ApiOperation(value = "批量删除附件", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @Deprecated
    @PostMapping("/batch/delete")
    @UserLog(type = OperateType.ATTACH_DELETE, description = "用户文件批量删除")
    public R<Boolean> deleteBatch(String[] ids) {
        return new R<>(attachmentOperaService.deleteBatch(ids));
    }

}
