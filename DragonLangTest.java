import org.antlr.v4.runtime.*;
import java.io.FileInputStream;
import java.io.IOException;

public class DragonLangTest {

    /** Custom error listener to format syntax errors. */
    static class DragonErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            Token tok = offendingSymbol instanceof Token ? (Token) offendingSymbol : null;
            String text = (tok == null) ? "<no token>" : tok.getText();
            String typeName = (tok == null)
                    ? "<unknown>"
                    : recognizer.getVocabulary().getSymbolicName(tok.getType());

            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.printf("SYNTAX ERROR at line %d, column %d%n", line, charPositionInLine);
            System.err.println("Message: " + msg);
            System.err.println("Offending token: '" + text + "' (type: " + typeName + ")");
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java DragonLangTest <input-file>");
            System.exit(1);
        }

        DragonLangParser parser = null;

        try (FileInputStream fis = new FileInputStream(args[0])) {
            // ANTLR 4.7.2: ANTLRInputStream is deprecated but OK here
            ANTLRInputStream input = new ANTLRInputStream(fis);

            DragonLangLexer lexer = new DragonLangLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new DragonLangParser(tokens);

            // Use custom error listener & error strategy
            parser.removeErrorListeners();
            parser.addErrorListener(new DragonErrorListener());
            parser.setErrorHandler(new DragonErrorStrategy());

            System.out.println("=== Dragon Book Parser - Appendix A.1 Implementation ===");
            System.out.println("=== Grammar Rule Tracing and Symbol Table Management ===");
            System.out.println("=== Error Recovery: Panic-mode + phrase-level ';' insertion ===");
            System.out.println();

            // Parse starting at 'program'
            parser.program();

        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (parser != null) {
                System.out.println();
                System.out.println("============================================================");
                System.out.println("=== PARSING RESULTS ===");
                int errs = parser.getNumberOfSyntaxErrors();
                if (errs > 0) {
                    System.out.println("X Parsing completed with " + errs + " errors");
                    System.out.println("Error recovery was attempted using panic-mode / phrase-level");
                } else {
                    System.out.println("+ Parsing completed successfully with no errors");
                }
                System.out.println("============================================================");

                System.out.println();
                System.out.println(">>> Ensuring symbol table is displayed...");
                parser.printFinalSymbolTable();   // calls @members method in DragonLang.g4
            }
        }
    }
}
