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
 * 
 * </p>
 *
 * @author Petty
 * @since 2019-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemResourceApi extends Model<SystemResourceApi> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据唯一标识符
     */
    private String id;

    /**
     * Api资源code
     */
    @TableId
    private String code;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 资源
     */
    private String permission;

    /**
     * 资源类型 
     */
    private String permissionType;

    /**
     * 状态
     */
    private String status;

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

    /**
     * 行政区划CODE
     */
    private String areaCode;

    /**
     * 行政区划名称
     */
    private String areaName;

    /**
     * 部门CODE
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
