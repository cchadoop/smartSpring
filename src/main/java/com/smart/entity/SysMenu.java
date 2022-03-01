package com.smart.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
@Getter
@Setter
@TableName("SYS_MENU")
@ApiModel(value = "SysMenu对象", description = "")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuId;

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单类型(地址/按钮)")
    private String menuType;

    @ApiModelProperty("父节点ID")
    private String parentId;

    @ApiModelProperty("地址")
    private String uri;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("序号")
    private Integer sortNum;

    @ApiModelProperty("权限标识")
    private String permissionCode;

    @ApiModelProperty("安全等级")
    private String securityLevel;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("修改人")
    private String updateUser;

    @ApiModelProperty("删除标识")
    private String deFlag;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }
}
