// Generated from DragonLang.g4 by ANTLR 4.7.2

    import java.util.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DragonLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, WHILE=3, DO=4, BREAK=5, TRUE=6, FALSE=7, INT=8, FLOAT=9, 
		BOOLEAN=10, ASSIGN=11, LT=12, GT=13, LE=14, GE=15, EQ=16, NE=17, AND=18, 
		OR=19, NOT=20, PLUS=21, MINUS=22, TIMES=23, DIVIDE=24, SEMICOLON=25, COMMA=26, 
		LPAREN=27, RPAREN=28, LBRACE=29, RBRACE=30, LBRACKET=31, RBRACKET=32, 
		NUM=33, REAL=34, ID=35, LINE_COMMENT=36, BLOCK_COMMENT=37, WS=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "WHILE", "DO", "BREAK", "TRUE", "FALSE", "INT", "FLOAT", 
			"BOOLEAN", "ASSIGN", "LT", "GT", "LE", "GE", "EQ", "NE", "AND", "OR", 
			"NOT", "PLUS", "MINUS", "TIMES", "DIVIDE", "SEMICOLON", "COMMA", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "NUM", "REAL", 
			"ID", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'while'", "'do'", "'break'", "'true'", "'false'", 
			"'int'", "'float'", "'boolean'", "'='", "'<'", "'>'", "'<='", "'>='", 
			"'=='", "'!='", "'&&'", "'||'", "'!'", "'+'", "'-'", "'*'", "'/'", "';'", 
			"','", "'('", "')'", "'{'", "'}'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "WHILE", "DO", "BREAK", "TRUE", "FALSE", "INT", "FLOAT", 
			"BOOLEAN", "ASSIGN", "LT", "GT", "LE", "GE", "EQ", "NE", "AND", "OR", 
			"NOT", "PLUS", "MINUS", "TIMES", "DIVIDE", "SEMICOLON", "COMMA", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "NUM", "REAL", 
			"ID", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	    // =============== Symbol table & scopes ===============

	    private Env currentEnv = null;    // current environment
	    private int scopeLevel = 0;       // nesting level of Env chain

	    // Stack: whether each parsed block actually pushed a scope
	    private java.util.Deque<Boolean> blockPushed = new java.util.ArrayDeque<>();

	    // Block nesting depth for { ... }:
	    //  blockDepth == 0 : program's outermost block
	    private int blockDepth = 0;

	    // Called at start of program
	    public void initializeGlobalScope() {
	        currentEnv = new Env(null);  // global scope (outermost)
	        scopeLevel = 0;
	        blockDepth = 0;
	    }

	    // Create new Env for nested block
	    public void enterScope() {
	        currentEnv = new Env(currentEnv);
	        scopeLevel++;
	        System.out.println(">>> Entering new scope (Level " + scopeLevel + ")");
	    }

	    // Pop Env at end of nested block
	    public void exitScope() {
	        if (currentEnv != null && currentEnv.prev != null) {
	            currentEnv = currentEnv.prev;
	            System.out.println(">>> Exiting scope (returning to Level " + (scopeLevel - 1) + ")");
	            scopeLevel--;
	        }
	    }

	    private void addIdentifier(String name, Type t) {
	        if (currentEnv == null || t == null) return;
	        Symbol sym = new Symbol(name, t);     // matches Symbol(String, Type)
	        currentEnv.put(name, sym);
	        System.out.println(">>> Added to symbol table: " + sym);
	    }

	    private void noteLookup(String name) {
	        if (currentEnv == null) return;
	        Symbol s = currentEnv.get(name);
	        if (s == null) {
	            System.out.println(">>> Warning: Undeclared identifier '" + name + "'");
	        } else {
	            System.out.println(">>> Found identifier: " + s);
	        }
	    }

	    public void printFinalSymbolTable() {
	        System.out.println("\n================================================================================");
	        System.out.println("=== FINAL SYMBOL TABLE STATE ===\n");
	        if (currentEnv != null) {
	            currentEnv.printSymbolTable();   // your Env.printSymbolTable()
	        }
	        System.out.println("================================================================================");
	    }

	    // Build a base Type from basic token text
	    private Type makeBase(String txt) {
	        switch (txt) {
	            case "int":     return new Type(Type.BaseType.INT);
	            case "float":   return new Type(Type.BaseType.FLOAT);
	            case "boolean": return new Type(Type.BaseType.BOOLEAN);
	            default:        return null;
	        }
	    }


	public DragonLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DragonLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u00f7\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16"+
		"\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3"+
		" \3!\3!\3\"\6\"\u00b7\n\"\r\"\16\"\u00b8\3#\6#\u00bc\n#\r#\16#\u00bd\3"+
		"#\3#\6#\u00c2\n#\r#\16#\u00c3\3#\3#\5#\u00c8\n#\3#\6#\u00cb\n#\r#\16#"+
		"\u00cc\5#\u00cf\n#\3$\3$\7$\u00d3\n$\f$\16$\u00d6\13$\3%\3%\3%\3%\7%\u00dc"+
		"\n%\f%\16%\u00df\13%\3%\3%\3&\3&\3&\3&\7&\u00e7\n&\f&\16&\u00ea\13&\3"+
		"&\3&\3&\3&\3&\3\'\6\'\u00f2\n\'\r\'\16\'\u00f3\3\'\3\'\3\u00e8\2(\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(\3\2\t\3\2\62;\4\2GGgg\4\2--//\5\2C\\aac|\6\2\62;C\\a"+
		"ac|\4\2\f\f\17\17\5\2\13\f\16\17\"\"\2\u0100\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2"+
		"\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2"+
		"M\3\2\2\2\3O\3\2\2\2\5R\3\2\2\2\7W\3\2\2\2\t]\3\2\2\2\13`\3\2\2\2\rf\3"+
		"\2\2\2\17k\3\2\2\2\21q\3\2\2\2\23u\3\2\2\2\25{\3\2\2\2\27\u0083\3\2\2"+
		"\2\31\u0085\3\2\2\2\33\u0087\3\2\2\2\35\u0089\3\2\2\2\37\u008c\3\2\2\2"+
		"!\u008f\3\2\2\2#\u0092\3\2\2\2%\u0095\3\2\2\2\'\u0098\3\2\2\2)\u009b\3"+
		"\2\2\2+\u009d\3\2\2\2-\u009f\3\2\2\2/\u00a1\3\2\2\2\61\u00a3\3\2\2\2\63"+
		"\u00a5\3\2\2\2\65\u00a7\3\2\2\2\67\u00a9\3\2\2\29\u00ab\3\2\2\2;\u00ad"+
		"\3\2\2\2=\u00af\3\2\2\2?\u00b1\3\2\2\2A\u00b3\3\2\2\2C\u00b6\3\2\2\2E"+
		"\u00bb\3\2\2\2G\u00d0\3\2\2\2I\u00d7\3\2\2\2K\u00e2\3\2\2\2M\u00f1\3\2"+
		"\2\2OP\7k\2\2PQ\7h\2\2Q\4\3\2\2\2RS\7g\2\2ST\7n\2\2TU\7u\2\2UV\7g\2\2"+
		"V\6\3\2\2\2WX\7y\2\2XY\7j\2\2YZ\7k\2\2Z[\7n\2\2[\\\7g\2\2\\\b\3\2\2\2"+
		"]^\7f\2\2^_\7q\2\2_\n\3\2\2\2`a\7d\2\2ab\7t\2\2bc\7g\2\2cd\7c\2\2de\7"+
		"m\2\2e\f\3\2\2\2fg\7v\2\2gh\7t\2\2hi\7w\2\2ij\7g\2\2j\16\3\2\2\2kl\7h"+
		"\2\2lm\7c\2\2mn\7n\2\2no\7u\2\2op\7g\2\2p\20\3\2\2\2qr\7k\2\2rs\7p\2\2"+
		"st\7v\2\2t\22\3\2\2\2uv\7h\2\2vw\7n\2\2wx\7q\2\2xy\7c\2\2yz\7v\2\2z\24"+
		"\3\2\2\2{|\7d\2\2|}\7q\2\2}~\7q\2\2~\177\7n\2\2\177\u0080\7g\2\2\u0080"+
		"\u0081\7c\2\2\u0081\u0082\7p\2\2\u0082\26\3\2\2\2\u0083\u0084\7?\2\2\u0084"+
		"\30\3\2\2\2\u0085\u0086\7>\2\2\u0086\32\3\2\2\2\u0087\u0088\7@\2\2\u0088"+
		"\34\3\2\2\2\u0089\u008a\7>\2\2\u008a\u008b\7?\2\2\u008b\36\3\2\2\2\u008c"+
		"\u008d\7@\2\2\u008d\u008e\7?\2\2\u008e \3\2\2\2\u008f\u0090\7?\2\2\u0090"+
		"\u0091\7?\2\2\u0091\"\3\2\2\2\u0092\u0093\7#\2\2\u0093\u0094\7?\2\2\u0094"+
		"$\3\2\2\2\u0095\u0096\7(\2\2\u0096\u0097\7(\2\2\u0097&\3\2\2\2\u0098\u0099"+
		"\7~\2\2\u0099\u009a\7~\2\2\u009a(\3\2\2\2\u009b\u009c\7#\2\2\u009c*\3"+
		"\2\2\2\u009d\u009e\7-\2\2\u009e,\3\2\2\2\u009f\u00a0\7/\2\2\u00a0.\3\2"+
		"\2\2\u00a1\u00a2\7,\2\2\u00a2\60\3\2\2\2\u00a3\u00a4\7\61\2\2\u00a4\62"+
		"\3\2\2\2\u00a5\u00a6\7=\2\2\u00a6\64\3\2\2\2\u00a7\u00a8\7.\2\2\u00a8"+
		"\66\3\2\2\2\u00a9\u00aa\7*\2\2\u00aa8\3\2\2\2\u00ab\u00ac\7+\2\2\u00ac"+
		":\3\2\2\2\u00ad\u00ae\7}\2\2\u00ae<\3\2\2\2\u00af\u00b0\7\177\2\2\u00b0"+
		">\3\2\2\2\u00b1\u00b2\7]\2\2\u00b2@\3\2\2\2\u00b3\u00b4\7_\2\2\u00b4B"+
		"\3\2\2\2\u00b5\u00b7\t\2\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9D\3\2\2\2\u00ba\u00bc\t\2\2\2"+
		"\u00bb\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bb\3\2\2\2\u00bd\u00be"+
		"\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\7\60\2\2\u00c0\u00c2\t\2\2\2"+
		"\u00c1\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4"+
		"\3\2\2\2\u00c4\u00ce\3\2\2\2\u00c5\u00c7\t\3\2\2\u00c6\u00c8\t\4\2\2\u00c7"+
		"\u00c6\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00cb\t\2"+
		"\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc"+
		"\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce\u00c5\3\2\2\2\u00ce\u00cf\3\2"+
		"\2\2\u00cfF\3\2\2\2\u00d0\u00d4\t\5\2\2\u00d1\u00d3\t\6\2\2\u00d2\u00d1"+
		"\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5"+
		"H\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d8\7\61\2\2\u00d8\u00d9\7\61\2"+
		"\2\u00d9\u00dd\3\2\2\2\u00da\u00dc\n\7\2\2\u00db\u00da\3\2\2\2\u00dc\u00df"+
		"\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2\2\2\u00df"+
		"\u00dd\3\2\2\2\u00e0\u00e1\b%\2\2\u00e1J\3\2\2\2\u00e2\u00e3\7\61\2\2"+
		"\u00e3\u00e4\7,\2\2\u00e4\u00e8\3\2\2\2\u00e5\u00e7\13\2\2\2\u00e6\u00e5"+
		"\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9"+
		"\u00eb\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ec\7,\2\2\u00ec\u00ed\7\61"+
		"\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\b&\2\2\u00efL\3\2\2\2\u00f0\u00f2"+
		"\t\b\2\2\u00f1\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6\b\'\2\2\u00f6N\3\2\2\2"+
		"\r\2\u00b8\u00bd\u00c3\u00c7\u00cc\u00ce\u00d4\u00dd\u00e8\u00f3\3\b\2"+
		"\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}