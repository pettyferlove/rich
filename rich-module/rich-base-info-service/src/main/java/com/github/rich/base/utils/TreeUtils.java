package com.github.rich.base.utils;

import com.github.rich.base.dto.TreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Petty
 */
public class TreeUtils {
    public static <T extends TreeNode> List<T> buildTree(List<T> menus, String root) {
        List<T> roots = new ArrayList<>();
        Iterator<T> iterator = menus.iterator();
        while (iterator.hasNext()){
            T node = iterator.next();
            if(root.equals(node.getParentCode())){
                iterator.remove();
                roots.add(buildChildren(node,menus));
            }
        }
        return roots;
    }

    private static <T extends TreeNode> T buildChildren(T parent, List<T> menus){
        for (T node: menus) {
            if(parent.getCode().equals(node.getParentCode())){
                parent.addChildren(buildChildren(node,menus));
            }
        }
        return parent;
    }
}
