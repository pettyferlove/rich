package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色关联信息
 * </p>
 *
 * @author Petty
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUserRole extends Model<SystemUserRole> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId(value = "code", type = IdType.UUID)
    private String code;

    private String userCode;

    private String roleCode;

    /**
     * 客户端ID
     */
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
