package com.github.rich.base.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@Api(value = "字典类型信息", tags = {"字典类型信息接口"})
@RestController
@RequestMapping("/dict/type")
public class DictTypeController {
}
