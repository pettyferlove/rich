package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class SystemUserClient extends Model<SystemUserClient> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据唯一标识符
     */
    private Integer id;

    @TableId
    private String code;

    /**
     * USER_CODE
     */
    private String userCode;

    /**
     * 客户端名称
     */
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
