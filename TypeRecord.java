import java.util.ArrayList;
import java.util.List;

public class TypeRecord {
    public enum BaseType {
        INT("int"),
        FLOAT("float"),
        BOOLEAN("boolean");
        
        private final String name;
        
        BaseType(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    private BaseType baseType;
    private List<Integer> dimensions;
    
    public TypeRecord(BaseType baseType) {
        this.baseType = baseType;
        this.dimensions = new ArrayList<>();
    }
    
    public TypeRecord(BaseType baseType, List<Integer> dimensions) {
        this.baseType = baseType;
        this.dimensions = new ArrayList<>(dimensions);
    }
    
    public void addDimension(int size) {
        dimensions.add(size);
    }
    
    public BaseType getBaseType() {
        return baseType;
    }
    
    public List<Integer> getDimensions() {
        return new ArrayList<>(dimensions);
    }
    
    public boolean isArray() {
        return !dimensions.isEmpty();
    }
    
    public boolean isScalar() {
        return dimensions.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ").append(baseType.toString()).append(", [");
        
        for (int i = 0; i < dimensions.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(dimensions.get(i));
        }
        
        sb.append("] }");
        
        // Add classification
        if (isScalar()) {
            sb.append(" (scalar)");
        } else {
            sb.append(" (array)");
        }
        
        return sb.toString();
    }
    
    public String getDisplayType() {
        StringBuilder sb = new StringBuilder();
        sb.append(baseType.toString());
        
        for (Integer dim : dimensions) {
            sb.append("[").append(dim).append("]");
        }
        
        return sb.toString();
    }
}