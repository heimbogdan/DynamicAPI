<#ftl auto_esc=false>

package ro.helator.api.dynamic.controller.${serviceName};

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ${className}Request {

<#list fields as field>
    <#if field.isKey = true>
    @NotNull
    @NotEmpty
    private <#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name};

    public <#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> get${field.name?cap_first}() {
        return this.${field.name};
    }
    public void set${field.name?cap_first}(<#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name}) {
        this.${field.name} = ${field.name};
    }

    </#if>
</#list>

    public String generateKey() {
        return "${className}"<#list fields as field><#if field.isKey = true> + ${field.name}</#if></#list>;
    }
}