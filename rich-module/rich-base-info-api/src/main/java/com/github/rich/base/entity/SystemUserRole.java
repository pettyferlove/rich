package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关联信息
 * </p>
 *
 * @author Petty
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUserRole extends Model<SystemUserRole> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId
    private String code;

    private String userCode;

    private String roleCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
