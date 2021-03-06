package com.github.rich.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统消息
 * </p>
 *
 * @author Petty
 * @since 2019-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "SystemMessage对象", description = "系统消息")
public class SystemMessage extends Model<SystemMessage> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "具体内容")
    private String content;

    @ApiModelProperty(value = "消息产生时间")
    private String time;

    @ApiModelProperty(value = "消息类型 1系统消息 2业务消息（诸如处理通知等）")
    private Integer type;

    @ApiModelProperty(value = "业务名称")
    private String businessName;

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    @ApiModelProperty(value = "该业务对应的页面地址（对应前端路径）")
    private String businessPageAddress;

    @ApiModelProperty(value = "编辑地址（对应前端路径）")
    private String editPageAddress;

    @ApiModelProperty(value = "查看地址（对应前端路径）")
    private String viewPageAddress;

    @ApiModelProperty(value = "接收人UUID")
    private String receiver;

    @ApiModelProperty(value = "发送人UUID，如果是系统消息则同意为system")
    private String deliver;

    @ApiModelProperty(value = "消息级别 1 普通 2警告 3错误")
    private Integer level;

    @ApiModelProperty(value = "状态 1已读 0未读 默认为0")
    private Integer state;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
