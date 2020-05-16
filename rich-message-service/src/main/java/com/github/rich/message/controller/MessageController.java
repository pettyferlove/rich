package com.github.rich.message.controller;


import com.github.rich.common.core.domain.vo.R;
import com.github.rich.message.service.ISystemMessageService;
import com.github.rich.message.domain.vo.message.UserMessageVO;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public R<List<UserMessageVO>> loadUnread() {
        return new R<>(systemMessageService.loadUnread(Objects.requireNonNull(SecurityUtil.getUser()).getUserId()));
    }


    @ApiOperation(value = "变更状态为已读", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PutMapping("/read/{id}")
    public R<Boolean> get(@PathVariable String id) {
        return new R<>(systemMessageService.read(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), id));
    }
}
