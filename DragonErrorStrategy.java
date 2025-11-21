import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

/**
 * Custom error strategy for the DragonLang parser.
 * - Prints friendly banners
 * - Does phrase-level ';' insertion for declaration/statement tails
 * - Handles 'x += 10;' by skipping stray '+'
 * - Increments parser.errorCount via incError()
 */
public class DragonErrorStrategy extends DefaultErrorStrategy {

    //Each time a syntax error is reported, increments an internal counter inside the parser.
    private void count(Parser recognizer) {
        if (recognizer instanceof DragonLangParser) {
            ((DragonLangParser) recognizer).incError();
        }
    }
    //uniform error appearance
    private void banner(Parser recognizer, String msg, Token offending, int line, int col) {
        count(recognizer);
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.err.println("SYNTAX ERROR at line " + line + ", column " + col);
        System.err.println("Message: " + msg);
        if (offending != null) {
            System.err.println("Offending token: '" + offending.getText()
                               + "' (type: " + offending.getType() + ")");
        }
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    // Helper: detect "x += ..." pattern and treat as "x = ..."
    //"phrase-level repair", a key topic
    //Instead of reporting a fatal error, transform
    private boolean handlePlusAssign(Parser recognizer) {
        TokenStream tokens = recognizer.getInputStream();
        Token current = tokens.LT(1);   // ASSIGN
        Token previous = tokens.LT(-1); // PLUS, ideally

        if (previous != null &&
            previous.getType() == DragonLangParser.PLUS &&
            current.getType() == DragonLangParser.ASSIGN) {

            System.out.println(">>> Phrase-level recovery: skipping stray '+' in '+=', treating 'x += ...' as 'x = ...'");
            return true;
        }
        return false;
    }

    // Phrase-level: if expecting ';' but see a token that looks like the start
    // of the next declaration/statement, conceptually insert ';'
    //Look for tokens that start the next statement/decl - FIRST(stmt) and FIRST(decl)
    private boolean handleMissingSemicolon(Parser recognizer, Token offendingToken) {
        IntervalSet expecting = getExpectedTokens(recognizer);

        if (!expecting.contains(DragonLangParser.SEMICOLON)) {
            return false;
        }

        int ttype = offendingToken.getType();
        boolean looksLikeNext =
                ttype == DragonLangParser.IF      ||
                ttype == DragonLangParser.WHILE   ||
                ttype == DragonLangParser.DO      ||
                ttype == DragonLangParser.INT     ||
                ttype == DragonLangParser.FLOAT   ||
                ttype == DragonLangParser.BOOLEAN ||
                ttype == DragonLangParser.RBRACE  ||
                ttype == Token.EOF;

        if (!looksLikeNext) {
            return false;
        }

        System.out.println(">>> Phrase-level recovery: inserting missing ';' before '"
                           + offendingToken.getText() + "'");
        return true;
    }

    // --------- Overridden reporting methods ----------

    //x = ) ; mismatched input ')' expecting {'true','false','(',ID,...}
    @Override
    public void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        Token offending = e.getOffendingToken();
        IntervalSet expecting = e.getExpectedTokens();
        String tokenName = getTokenErrorDisplay(offending);
        String expected = expecting.toString(recognizer.getVocabulary());
        String msg = "mismatched input " + tokenName + " expecting " + expected;
        banner(recognizer, msg, offending,
               offending.getLine(), offending.getCharPositionInLine());
    }

    //if (x > 0 )   // missing '{'
    @Override
    public void reportMissingToken(Parser recognizer) {
        Token offending = recognizer.getCurrentToken();
        IntervalSet expecting = getExpectedTokens(recognizer);
        String expected = expecting.toString(recognizer.getVocabulary());
        String msg = "missing " + expected + " at '" + offending.getText() + "'";
        banner(recognizer, msg, offending,
               offending.getLine(), offending.getCharPositionInLine());
    }

    //x = 5;;     // unwanted second semicolon
    @Override
    public void reportUnwantedToken(Parser recognizer) {
        if (inErrorRecoveryMode(recognizer)) return;

        beginErrorCondition(recognizer);
        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        String expected = getExpectedTokens(recognizer).toString(recognizer.getVocabulary());
        String msg = "extraneous input " + tokenName + " expecting " + expected;
        banner(recognizer, msg, t, t.getLine(), t.getCharPositionInLine());
    }

    //if ( )
    @Override
    public void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        Token offending = e.getOffendingToken();
        String tokenName = getTokenErrorDisplay(offending);
        String msg = "no viable alternative at input " + tokenName;
        banner(recognizer, msg, offending,
               offending.getLine(), offending.getCharPositionInLine());
    }

    // -------------- Inline & panic-mode recovery hooks --------------

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        Token offending = recognizer.getCurrentToken();

        // Case 1: "x += 10;" → skip '+', treat as 'x = 10;'
        if (offending.getType() == DragonLangParser.ASSIGN && handlePlusAssign(recognizer)) {
            return super.recoverInline(recognizer);
        }

        // Case 2: missing ';' before next declaration/statement
        if (handleMissingSemicolon(recognizer, offending)) {
            // Let superclass do the synthetic insertion
            return super.recoverInline(recognizer);
        }

        // Otherwise default inline recovery
        return super.recoverInline(recognizer);
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        // Use standard ANTLR panic-mode skipping, 
        super.recover(recognizer, e);
    }

    @Override
    public void sync(Parser recognizer) {
        // Keep default sync; detailed messages come from report* methods.
        super.sync(recognizer);
    }
}
