import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.CommonToken;

/**
 * Custom error strategy for HW3.
 *
 * - Panic-mode recovery (Dragon Book 4.1.3–4.1.4):
 *   skip tokens until a synchronizing symbol (;, }, else, EOF).
 *
 * - Phrase-level recovery for missing semicolons:
 *   if ';' is expected and we see something that could legally start
 *   the next construct, we "insert" a virtual ';' and continue.
 */
public class DragonErrorStrategy extends DefaultErrorStrategy {

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        // Called when parser cannot match a rule
        System.err.println();
        System.err.println(">>> INITIATING PANIC-MODE ERROR RECOVERY <<<");
        System.err.println(">>> Context (rule stack): " + recognizer.getRuleInvocationStack());

        Token t = recognizer.getCurrentToken();
        int skipped = 0;
        IntervalSet sync = getSynchronizationSet(recognizer);

        // Panic-mode: skip tokens until we hit a synchronizing token
        while (t.getType() != Token.EOF && !sync.contains(t.getType())) {
            System.err.println(">>> Skipping token: '" + t.getText() + "'");
            recognizer.consume();
            t = recognizer.getCurrentToken();
            skipped++;
            if (skipped > 100) {
                System.err.println(">>> Too many tokens skipped; giving up on this error.");
                break;
            }
        }

        System.err.println(">>> Synchronized on '" + t.getText() +
                           "' after skipping " + skipped + " tokens");
        // Let the parser continue from here
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        /*
         * Phrase-level recovery:
         * If parser expects ';' and we see something that looks like the
         * beginning of the next statement/block, we "pretend" a ';'
         * was present and continue.
         */
        IntervalSet expecting = recognizer.getExpectedTokens();
        DragonLangParser p = (DragonLangParser) recognizer;
        int semicolonType = p.SEMICOLON;
        Token current = recognizer.getCurrentToken();

        if (expecting.contains(semicolonType) && probableMissingSemicolon(p, current)) {
            System.err.println(">>> Phrase-level recovery: inserting missing ';' before '" +
                               current.getText() + "'");
            reportMissingToken(recognizer);

            CommonToken missing = new CommonToken(semicolonType, ";");
            Token prev = (current.getTokenIndex() > 0)
                    ? recognizer.getInputStream().get(current.getTokenIndex() - 1)
                    : current;

            missing.setLine(prev.getLine());
            missing.setCharPositionInLine(prev.getCharPositionInLine() + prev.getText().length());
            return missing;
        }

        // Fallback: default single-token deletion/insertion
        return super.recoverInline(recognizer);
    }

    /**
     * Synchronization set for panic-mode:
     *  ;    — end of statement/declaration
     *  }    — end of block
     *  else — start of else-part
     *  EOF  — end of file
     */
    private IntervalSet getSynchronizationSet(Parser recognizer) {
        IntervalSet s = new IntervalSet();
        DragonLangParser p = (DragonLangParser) recognizer;
        s.add(p.SEMICOLON);
        s.add(p.RBRACE);
        s.add(p.ELSE);
        s.add(Token.EOF);
        return s;
    }

    /**
     * Heuristic to decide if a missing ';' is likely:
     * We expect ';', but see a token that could validly start the
     * *next* statement or close the block.
     */
    private boolean probableMissingSemicolon(DragonLangParser p, Token current) {
        int t = current.getType();
        if (t == Token.EOF) return true;
        return t == p.RBRACE   // end of block
            || t == p.IF
            || t == p.WHILE
            || t == p.DO
            || t == p.BREAK;
    }
}
