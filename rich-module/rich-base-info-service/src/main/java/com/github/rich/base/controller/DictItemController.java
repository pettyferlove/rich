package com.github.rich.base.controller;

import com.github.rich.base.service.ISystemDictItemService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@Api(value = "字典项信息", tags = {"字典项信息接口"})
@RestController
@RequestMapping("/dict/item")
public class DictItemController {

    private final ISystemDictItemService systemDictItemService;

    public DictItemController(ISystemDictItemService systemDictItemService) {
        this.systemDictItemService = systemDictItemService;
    }
}
