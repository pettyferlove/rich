package com.github.rich.message.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.common.core.vo.R;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.service.ISystemMessageService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统消息 接口控制器
 * </p>
 *
 * @author Petty
 * @since 2019-06-27
 */
@Api(value = "系统消息", tags = {"系统消息接口"})
@RestController
@RequestMapping("/message")
public class MessageController {
    private final ISystemMessageService systemMessageService;

    public MessageController(ISystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @ApiOperation(value = "获取系统消息列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemMessage", value = "systemMessage", dataTypeClass = SystemMessage.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemMessage systemMessage, Page<SystemMessage> page) {
        return new R<>(systemMessageService.page(systemMessage, page));
    }


    @ApiOperation(value = "获取系统消息详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemMessage> get(@PathVariable String id) {
        return new R<>(systemMessageService.get(id));
    }
}
