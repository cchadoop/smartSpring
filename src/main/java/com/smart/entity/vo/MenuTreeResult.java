package com.smart.entity.vo;


import com.smart.entity.SysMenu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiaoQinZhou
 * @date: 2021/4/7 16:47
 */
public class MenuTreeResult extends SysMenu implements Serializable {

    private static final long serialVersionUID = -9020248992966967320L;
    private List<MenuTreeResult> children;

    private String name;
    private String path;

    private Map<String, Object> meta = new HashMap<>();

    public List<MenuTreeResult> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeResult> children) {
        this.children = children;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}
