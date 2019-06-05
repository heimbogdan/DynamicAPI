<#ftl auto_esc=false>

package ro.helator.api.dynamic.controller.${serviceName};

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.helator.api.dynamic.controller.dto.SimpleResponseDTO;
import ro.helator.api.dynamic.jsondb.JsonDBManager;

@RestController
@RequestMapping("/mock/${serviceName}")
public class ${serviceName?cap_first}Controller {

<#list methods as method>
    @PostMapping("/add${method.response?cap_first}")
    public SimpleResponseDTO add${method.response?cap_first}(@RequestBody ${method.response} request){
        try {
            JsonDBManager.update(request);
        } catch (Exception e) {
            return SimpleResponseDTO.builder()
                .status("error")
                .message(e.getMessage())
                .build();
        }
        return SimpleResponseDTO.builder()
            .status("success")
            .build();
    }

    @PostMapping("/${method.name}")
    public ${method.response} ${method.name}(@RequestBody ${method.response}Request request) {
        return JsonDBManager.findById(request.generateKey(), ${method.response}.class);
    }

</#list>



}