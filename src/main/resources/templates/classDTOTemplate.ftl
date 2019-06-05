<#ftl auto_esc=false>

package ro.helator.api.dynamic.controller.${serviceName};

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "${serviceName}_${className?lower_case}", schemaVersion = "1.0.0")
public class ${className?cap_first} {

    @Id
    @JsonIgnore
    private String key;

<#list fields as field>
    <#if field.isKey>
    @NotNull
    @NotEmpty
    </#if>
    private <#if field.isArray = true>List<${field.type}><#else>${field.type}</#if> ${field.name};

    public ${field.type} get${field.name?cap_first}() {
        return this.${field.name};
    }
    public void set${field.name?cap_first}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }

</#list>

    public ${className}() {}
<@compress single_line=true>
    public ${className}(
    <#list fields as field>${field.type} ${field.name}<#if field?index < fields?size - 1>, </#if>
    </#list>) {
</@compress>

<#list fields as field>
        this.${field.name} = ${field.name};
</#list>
    }

    public void generateKey() {
        this.key = "${className}"<#list fields as field><#if field.isKey = true> + ${field.name}</#if></#list>;
    }

}