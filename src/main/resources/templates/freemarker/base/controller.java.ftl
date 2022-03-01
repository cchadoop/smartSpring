package ${package.Controller};

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.smart.contant.enums.DeFlagEnum;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    <#assign service=table.serviceName?uncap_first/>
    @Autowired
    private  ${table.serviceName} ${service};

    @GetMapping("/list")
    public List<${entity}> list(${entity} entity){
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return ${service}.list(wrapper);
    }

    @GetMapping("/page")
    public IPage<${entity}> page(@RequestParam(value = "current", defaultValue = "1") int current,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               ${entity} entity) {
        IPage<${entity}> page = new Page<>(current, size);
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return ${service}.page(page, wrapper);
    }

    @PostMapping("/save")
    public ${entity} save(@RequestBody ${entity} entity) {
        if (StrUtil.isEmpty(entity.getId())) {
            entity.setId(IdUtil.simpleUUID());
            entity.setCreateTime(LocalDateTime.now());
            ${service}.save(entity);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
            ${service}.updateById(entity);
        }
        return entity;
    }

    @DeleteMapping("/remove")
    public boolean remove(@RequestParam("id") String id) {
        ${entity} entity = ${service}.getById(id);
        if (Objects.nonNull(entity)) {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setDeFlag(DeFlagEnum.DELETE.getValue());
            return ${service}.updateById(entity);
        }
        return false;
    }
}
</#if>
