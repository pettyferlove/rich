package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色接口关联
 * </p>
 *
 * @author Petty
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemRoleApi extends Model<SystemRoleApi> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId
    private String code;

    private String roleCode;

    private String apiCode;

    /**
     * 客户端ID
     */
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
