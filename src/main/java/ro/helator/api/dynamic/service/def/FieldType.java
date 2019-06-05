package ro.helator.api.dynamic.service.def;

public enum FieldType {

    STRING("string", String.class),
    INTEGER("int", Integer.class),
    BOOLEAN("boolean", Boolean.class);

    private String type;
    private Class clazz;

    FieldType(String type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getClazzName(){
        return this.clazz.getName();
    }

    public String getShortName() { return this.clazz.getSimpleName();}

    public static FieldType getType(String type) {
        switch (type) {
            case "string":
                return FieldType.STRING;
            case "int":
                return FieldType.INTEGER;
            case "boolean":
                return FieldType.BOOLEAN;
            default:
                return null;
        }

    }
}
