package ro.helator.api.dynamic.service.def;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceModel {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private List<MethodModel> methods;

    @NotNull
    @NotEmpty
    private List<ClassModel> dataModel;
}
