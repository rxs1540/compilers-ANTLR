import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Type {
    public enum BaseType {
        INT, FLOAT, BOOLEAN
    }

    private BaseType base;
    private List<Integer> dims = new ArrayList<>();

    public Type(BaseType base) {
        this.base = base;
    }

    public void addDimension(int n) {
        dims.add(n);
    }

    public BaseType getBase() {
        return base;
    }

    public List<Integer> getDimensions() {
        return dims;
    }

    public boolean isScalar() {
        return dims.isEmpty();
    }

    @Override
    public String toString() {
        String baseStr;
        switch (base) {
            case INT: baseStr = "int"; break;
            case FLOAT: baseStr = "float"; break;
            case BOOLEAN: baseStr = "boolean"; break;
            default: baseStr = "<?>"; break;
        }

        if (dims.isEmpty()) {
            return "{ " + baseStr + " } (scalar)";
        } else {
            String dimStr = dims.stream()
                                .map(d -> "[" + d + "]")
                                .collect(Collectors.joining());
            return "{ " + baseStr + dimStr + " } (array)";
        }
    }
}
