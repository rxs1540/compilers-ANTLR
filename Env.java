import java.util.*;

public class Env {
    final Map<String, Symbol> table = new LinkedHashMap<>();
    public Env prev;

    public Env(Env p) { this.prev = p; }

    public void put(String name, Symbol sym) { table.put(name, sym); }

    public Symbol get(String name) {
        for (Env e = this; e != null; e = e.prev) {
            Symbol s = e.table.get(name);
            if (s != null) return s;
        }
        return null;
    }

    /** Print entire chain from GLOBAL (outermost) to INNERMOST (this). */
    public void printSymbolTable() {
        // climb to global
        Env g = this;
        while (g != null && g.prev != null) g = g.prev;

        // collect from global to innermost
        List<Env> chain = new ArrayList<>();
        for (Env e = g; e != null; e = e.prev) chain.add(e);

        System.out.println("GLOBAL → INNERMOST (Scope indices: 0 = global)");
        for (int i = 0; i < chain.size(); i++) {
            Env e = chain.get(i);
            System.out.println("+---------------- SCOPE " + i + " ----------------+");
            if (e.table.isEmpty()) {
                System.out.println("(empty)");
            } else {
                for (Map.Entry<String, Symbol> ent : e.table.entrySet()) {
                    // relies on your Symbol.toString() to format name/type nicely
                    System.out.println(ent.getValue());
                }
            }
        }
    }
}
