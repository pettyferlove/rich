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
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemApiResource extends Model<SystemApiResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据唯一标识符
     */
    private String id;

    /**
     * Api资源code
     */
    @TableId
    private String apiCode;

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
     * 状态 0 无效 1有效
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
     * 客户端ID
     */
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
