package ro.helator.api.dynamic.service;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.helator.api.dynamic.JavaCompilerUtil;
import ro.helator.api.dynamic.service.def.ClassModel;
import ro.helator.api.dynamic.service.def.Field;
import ro.helator.api.dynamic.service.def.MethodModel;
import ro.helator.api.dynamic.service.def.ServiceModel;

@Service
@Slf4j
public class DynamicAPIService {


    public void generateAPI(ServiceModel serviceModel) throws Exception {

        convertFieldType(serviceModel);
        Set<String> dtoClasses = getDtoClasses(serviceModel.getMethods());

        Map<String, String> sourceClasses = new HashMap<>();

        for (ClassModel classModel : serviceModel.getDataModel()) {
            String className = classModel.getName();
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("serviceName", serviceModel.getName());
            mapping.put("className", className);
            mapping.put("fields", classModel.getFields());

            if (dtoClasses.contains(className)) {
                sourceClasses.put(className, generateClassDTO(mapping));
                sourceClasses.put(className + "Request", generateClassDTORequest(mapping));
            } else {
                sourceClasses.put(className, generateClass(mapping));
            }
        }
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("serviceName", serviceModel.getName());
        mapping.put("methods", serviceModel.getMethods());
        sourceClasses.put(serviceModel.getName() + "Controller", generateClassController(mapping));

        JavaCompilerUtil.compileClasses(serviceModel.getName(), sourceClasses);

    }

    private void convertFieldType(ServiceModel serviceModel) {
        for (ClassModel classModel : serviceModel.getDataModel()) {
            for (Field field : classModel.getFields()) {
                field.setType(field.fieldType() != null ? field.fieldType().getShortName() : field.getType());
            }
        }
    }

    private Set<String> getDtoClasses(List<MethodModel> list) {
        Set<String> dtoClasses = new HashSet<>();
        for (MethodModel methodModel : list) {
            dtoClasses.add(methodModel.getResponse());
        }
        return dtoClasses;
    }

    private String generateClassDTO(Map<String, Object> mapping) throws Exception {

        Template template = getTemplate("classDTOTemplate.ftl");
        Writer writer = new StringWriter();
        template.process(mapping, writer);

        return writer.toString();
    }

    private String generateClassDTORequest(Map<String, Object> mapping) throws Exception {

        Template template = getTemplate("classDTORequestTemplate.ftl");
        Writer writer = new StringWriter();
        template.process(mapping, writer);

        return writer.toString();
    }

    private String generateClass(Map<String, Object> mapping) throws Exception {

        Template template = getTemplate("classTemplate.ftl");
        Writer writer = new StringWriter();
        template.process(mapping, writer);

        return writer.toString();
    }

    private String generateClassController(Map<String, Object> mapping) throws Exception {
        Template template = getTemplate("controllerTemplate.ftl");
        Writer writer = new StringWriter();
        template.process(mapping, writer);

        return writer.toString();
    }

    private Template getTemplate(String name) throws Exception {
        ClassTemplateLoader classTemplateLoader =
            new ClassTemplateLoader(DynamicAPIService.class, "/templates");
        Configuration ctlConfiguration = new Configuration(Configuration.getVersion());
        ctlConfiguration.setTemplateLoader(classTemplateLoader);
        ctlConfiguration.setDefaultEncoding("UTF-8");
        ctlConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        ctlConfiguration.setLogTemplateExceptions(false);
        return ctlConfiguration.getTemplate(name);
    }
}
