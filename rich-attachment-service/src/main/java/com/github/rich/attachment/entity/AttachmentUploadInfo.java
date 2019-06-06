package com.github.rich.attachment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 
 * </p>
 *
 * @author Petty
 * @since 2019-06-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="AttachmentUploadInfo对象", description="")
public class AttachmentUploadInfo extends Model<AttachmentUploadInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "文件名")
    @TableField("fileName")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @TableField("fileType")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "储存类型")
    @TableField("storageType")
    private Integer storageType;

    @ApiModelProperty(value = "MD5值")
    private String md5;

    @ApiModelProperty(value = "地址")
    private String path;

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
    private LocalDateTime modifierTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
