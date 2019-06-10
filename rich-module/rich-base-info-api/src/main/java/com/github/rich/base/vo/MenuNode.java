package com.github.rich.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.base.dto.TreeNode;
import lombok.*;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuNode extends TreeNode {
    private static final long serialVersionUID = 8890625026946419517L;

    private String id;

    private String parentId;

    private String title;

    private String icon;

    private Integer visitType;

    private String routeName;

    private Long sort;

    private Integer permissionType;

    private String permission;
}
