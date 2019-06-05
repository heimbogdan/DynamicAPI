package ro.helator.api.dynamic.service.def;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class Field {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "(string)|(integer)|(decimal)|(boolean)")
    private String type;

    private Boolean isKey;
    private Boolean isArray;

    public FieldType fieldType(){
        return FieldType.getType(this.type);
    }
}
