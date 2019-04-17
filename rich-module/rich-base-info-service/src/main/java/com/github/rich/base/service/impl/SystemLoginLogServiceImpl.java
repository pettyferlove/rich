package com.github.rich.base.service.impl;

import com.github.rich.base.entity.SystemLoginLog;
import com.github.rich.base.mapper.SystemLoginLogMapper;
import com.github.rich.base.service.ISystemLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-04-17
 */
@Service
public class SystemLoginLogServiceImpl extends ServiceImpl<SystemLoginLogMapper, SystemLoginLog> implements ISystemLoginLogService {

}
