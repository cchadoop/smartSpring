package com.smart.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("SYS_ROLE")
@ApiModel(value = "SysRole对象", description = "")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ROLE_ID")
    private String roleId;

    @ApiModelProperty("角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty("角色说明")
    @TableField("DESCRIBE")
    private String describe;

    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    @ApiModelProperty("修改人")
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty("删除标识")
    @TableField("DE_FLAG")
    private String deFlag;


}
