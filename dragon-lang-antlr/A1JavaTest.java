import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DragonLangTest {
    
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java DragonLangTest <input-file>");
            System.exit(1);
        }
        
        // Create input stream from file
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
        
        // Create lexer
        DragonLangLexer lexer = new DragonLangLexer(input);
        
        // Create token printer
        TokenPrinter tokenPrinter = new TokenPrinter(lexer.getVocabulary());
        
        // Print all tokens
        Token token;
        Set<String> symbolTable = new HashSet<>();
        
        while ((token = lexer.nextToken()).getType() != Token.EOF) {
            tokenPrinter.printToken(token);
            
            // Collect identifiers for symbol table
            if (token.getType() == DragonLangLexer.ID) {
                symbolTable.add(token.getText());
            }
        }
        
        // Print symbol table
        System.out.println("\n--- Symbol Table ---");
        if (symbolTable.isEmpty()) {
            System.out.println("(empty)");
        } else {
            List<String> sortedSymbols = new ArrayList<>(symbolTable);
            Collections.sort(sortedSymbols);
            for (String symbol : sortedSymbols) {
                System.out.println(symbol);
            }
        }
    }
    
    static class TokenPrinter {
        private Vocabulary vocabulary;
        
        public TokenPrinter(Vocabulary vocabulary) {
            this.vocabulary = vocabulary;
        }
        
        public void printToken(Token token) {
            String tokenName = vocabulary.getSymbolicName(token.getType());
            
            switch (tokenName) {
                case "INT":
                case "FLOAT":
                case "BOOLEAN":
                    System.out.println("basic");
                    break;
                case "UNRECOGNIZED":
                    System.out.println("unrecognized: " + token.getText());
                    break;
                case "ID":
                    System.out.println("id");
                    break;
                case "NUM":
                    System.out.println("num");
                    break;
                case "REAL":
                    System.out.println("real");
                    break;
                default:
                    if (isOperatorOrDelimiter(tokenName)) {
                        System.out.println(token.getText());
                    } else {
                        System.out.println(tokenName.toLowerCase());
                    }
                    break;
            }
        }
        
        private boolean isOperatorOrDelimiter(String tokenName) {
            return tokenName.matches("(ASSIGN|LT|GT|LE|GE|EQ|NE|AND|OR|NOT|PLUS|MINUS|TIMES|DIVIDE|" +
                                   "SEMICOLON|COMMA|LPAREN|RPAREN|LBRACE|RBRACE|LBRACKET|RBRACKET)");
        }
    }
}