package ${package.Controller};


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.common.core.vo.R;
import org.springframework.web.bind.annotation.*;
import com.github.rich.log.annotation.UserOperateLog;
import com.github.rich.log.constants.OperateType;
import org.springframework.security.access.prepost.PreAuthorize;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.*;

/**
 * <p>
 * ${table.comment!} 接口控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value = "${table.comment}", tags = {"${table.comment}接口"})
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    private final ${table.serviceName} ${entity?uncap_first}Service;

    public ${table.controllerName}(${table.serviceName} ${entity?uncap_first}Service) {
        this.${entity?uncap_first}Service = ${entity?uncap_first}Service;
    }

    @ApiOperation(value = "获取${table.comment}列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "${entity?uncap_first}", value = "${entity?uncap_first}", dataTypeClass = ${entity}.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(${entity} ${entity?uncap_first}, Page<${entity}> page) {
        return new R<>(${entity?uncap_first}Service.page(${entity?uncap_first}, page));
    }


    @ApiOperation(value = "获取${table.comment}详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
            @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<${entity}> get(@PathVariable String id) {
        return new R<>(${entity?uncap_first}Service.get(id));
    }

    @ApiOperation(value = "创建${table.comment}", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
            @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "${entity?uncap_first}", value = "${entity?uncap_first}", dataTypeClass = ${entity}.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @UserOperateLog(type = OperateType.ADD, description = "创建${table.comment}")
    public R<String> create(${entity} ${entity?uncap_first}) {
        return new R<>(${entity?uncap_first}Service.create(${entity?uncap_first}));
    }

    @ApiOperation(value = "更新${table.comment}", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "${entity?uncap_first}", value = "${entity?uncap_first}", dataTypeClass = ${entity}.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @UserOperateLog(type = OperateType.UPDATE, description = "更新${table.comment}")
    public R<Boolean> update(${entity} ${entity?uncap_first}) {
        return new R<>(${entity?uncap_first}Service.update(${entity?uncap_first}));
    }

    @ApiOperation(value = "删除${table.comment}", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
    @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @UserOperateLog(type = OperateType.DELETE, description = "删除${table.comment}")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(${entity?uncap_first}Service.delete(id));
    }
}
</#if>
