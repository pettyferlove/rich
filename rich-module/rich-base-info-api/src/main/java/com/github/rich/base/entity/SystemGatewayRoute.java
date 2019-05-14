package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemGatewayRoute extends Model<SystemGatewayRoute> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据唯一标识符
     */
    private String id;

    /**
     * 网关CODE
     */
    @TableId(value = "code", type = IdType.ID_WORKER_STR)
    private String code;

    /**
     * Route JSON
     */
    private String route;

    /**
     * 状态 1有效 0无效 默认为1
     */
    private Integer status;

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
        return this.code;
    }

}
