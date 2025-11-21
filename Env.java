import java.util.*;

public class Env {
    private Map<String, Symbol> table = new HashMap<>();
    protected Env prev;
    private static int nextId = 0;
    private int id;

    public Env(Env p) {
        this.prev = p;
        this.id = nextId++;
    }

    public void put(String name, Symbol sym) {
        table.put(name, sym);
    }

    public Symbol get(String name) {
        for (Env e = this; e != null; e = e.prev) {
            Symbol s = e.table.get(name);
            if (s != null) return s;
        }
        return null;
    }

    public Symbol getLocal(String name) {
        return table.get(name);
    }

    public int getId() {
        return id;
    }

    public void printSymbolTable() {
        // Build chain from current to global
        List<Env> chain = new ArrayList<>();
        for (Env e = this; e != null; e = e.prev) {
            chain.add(e);
        }
        // Now reverse: global first, innermost last
        Collections.reverse(chain);

        int globalId = chain.get(0).getId();
        System.out.println("GLOBAL → INNERMOST (Scope indices: " + globalId + " = global)");

        for (Env e : chain) {
            System.out.println("+---------------- SCOPE " + e.getId() + " ----------------+");
            if (e.table.isEmpty()) {
                System.out.println("(empty scope)");
            } else {
                for (Map.Entry<String, Symbol> entry : e.table.entrySet()) {
                    System.out.println(entry.getValue());
                }
            }
            System.out.println("================================================================================");
        }
    }
}
