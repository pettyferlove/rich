package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户登录日志
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLoginLog extends Model<SystemLoginLog> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 日志code
     */
    @TableId
    private String logCode;

    /**
     * 用户登录名
     */
    private String loginCode;

    /**
     * 用户账号
     */
    private String loginName;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * ip地址
     */
    private String loginIp;

    /**
     * 所属操作系统
     */
    private String userAgent;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 删除标记 0 未删除 1 删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime modifierTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
