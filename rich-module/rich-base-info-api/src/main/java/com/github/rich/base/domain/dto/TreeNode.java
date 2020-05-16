package com.github.rich.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 204211858352702295L;
    private String id;
    private String parentId;
    List<TreeNode> children;
    public void addChildren(TreeNode node){
        if(children==null){
            children = new ArrayList<>();
        }
        children.add(node);
    }
}
