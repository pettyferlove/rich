package com.github.rich.base.constants;

/**
 * 缓存NameSpace
 * @author Petty
 */
public interface CacheConstant {
    String DEFAULT_PREFIX = "rich_base:";
    String INNER_API_PREFIX = DEFAULT_PREFIX + "base_inner:";
    String OUTER_API_PREFIX = DEFAULT_PREFIX + "base_outer:";


    String DICT_ITEM_RELEVANCE_CACHE = OUTER_API_PREFIX + "base-dict-type-item-relevance";
    String USER_ROLE_RELEVANCE_CACHE = OUTER_API_PREFIX + "base-user-role-relevance";
    String MENU_ROLE_RELEVANCE_CACHE = OUTER_API_PREFIX + "base-menu-role-relevance";


    String MENU_ROLE_RELEVANCE_KEYS_CACHE = OUTER_API_PREFIX + "base-menu-role-relevance-keys";
}
