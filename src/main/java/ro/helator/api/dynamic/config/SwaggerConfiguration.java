package ro.helator.api.dynamic.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import(FabricCorsConfiguration.class)
@EnableSwagger2
public class SwaggerConfiguration {

    public static Docket docket;

    @Bean
    public Docket api() {
        if (docket == null) {
            Set<String> protocols = new HashSet<>();
            protocols.add("http");
            ApiInfo info = new ApiInfo("Dynamic API", "Dynamic API", "1.0", "urn:tos",
                new Contact("Heim Bogdan", "", ""), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
            docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ro.helator.api.dynamic"))
                .paths(PathSelectors.any())
                .build().apiInfo(info).protocols(protocols);
        }
        return docket;
    }
}
