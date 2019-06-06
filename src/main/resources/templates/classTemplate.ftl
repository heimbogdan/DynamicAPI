<#ftl auto_esc=false>

package ro.helator.api.dynamic.controller.${serviceName};

import java.util.List;

public class ${className?cap_first} {

<#list fields as field>
    private <#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name};

    public <#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> get${field.name?cap_first}() {
        return this.${field.name};
    }
    public void set${field.name?cap_first}(<#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name}) {
        this.${field.name} = ${field.name};
    }

</#list>

    public ${className}() {}
<@compress single_line=true>
    public ${className}(
    <#list fields as field><#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name}<#if field?index < fields?size - 1>, </#if>
    </#list>) {
</@compress>

<#list fields as field>
        this.${field.name} = ${field.name};
</#list>
    }

}