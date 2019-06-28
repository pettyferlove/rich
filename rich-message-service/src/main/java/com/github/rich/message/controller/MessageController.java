package com.github.rich.message.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.common.core.vo.R;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.service.ISystemMessageService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping
public class MessageController {
    private final ISystemMessageService systemMessageService;

    public MessageController(ISystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @ApiOperation(value = "获取用户未读消息列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("load/unread")
    public R<List<UserGeneralMessage>> loadUnread() {
        return new R<>(systemMessageService.loadUnread());
    }


    @ApiOperation(value = "变更状态为已读", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PutMapping("/read/{id}")
    public R<Boolean> get(@PathVariable String id) {
        return new R<>(systemMessageService.read(id));
    }
}
