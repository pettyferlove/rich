package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 行政区划信息表
 * </p>
 *
 * @author Petty
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemRegion extends Model<SystemRegion> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 行政区划编码
     */
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    /**
     * 行政区划父级编码
     */
    private String parentCode;

    /**
     * 行政区划名称
     */
    private String regionName;

    /**
     * 行政区划类型：0=国家；1=省；2=市；3=区／县
     */
    private Integer regionType;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 客户端ID
     */
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
