public class Symbol {
    private String name;
    private Type type;
    
    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return name + " -> " + type.toString();
    }
}