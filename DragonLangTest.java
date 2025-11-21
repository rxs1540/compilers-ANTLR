import org.antlr.v4.runtime.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Driver class to run the DragonLang parser on an input file.
 */
public class DragonLangTest {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java DragonLangTest <input-file>");
            System.exit(1);
        }

        String filename = args[0];

        try (FileInputStream fis = new FileInputStream(filename)) {
            CharStream input = CharStreams.fromStream(fis);

            DragonLangLexer lexer = new DragonLangLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            DragonLangParser parser = new DragonLangParser(tokens);

            System.out.println("=== Dragon Book Parser - Appendix A.1 Implementation ===");
            System.out.println("=== Grammar Rule Tracing and Symbol Table Management ===");
            System.out.println("=== Error Recovery: Panic-mode + phrase-level ';' insertion ===");
            System.out.println();

            // Custom error handler
            parser.setErrorHandler(new DragonErrorStrategy());

            // We don't want ANTLR's default one-line error messages;
            // we'll rely on DragonErrorStrategy banners.
            parser.removeErrorListeners();

            // Parse starting from 'program'
            parser.program();

            System.out.println();
            System.out.println("============================================================");
            System.out.println("=== PARSING RESULTS ===");
            if (parser.errorCount > 0) {
                System.out.println("X Parsing completed with " + parser.errorCount + " errors");
            } else {
                System.out.println("+ Parsing completed successfully with no errors");
            }
            System.out.println("============================================================");

        } catch (IOException e) {
            System.err.println("I/O error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
}
