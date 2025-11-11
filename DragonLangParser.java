// Generated from DragonLang.g4 by ANTLR 4.7.2

    import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DragonLangParser extends Parser {
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
	public static final int
		RULE_program = 0, RULE_block = 1, RULE_decls = 2, RULE_decl = 3, RULE_id_list = 4, 
		RULE_type_rule = 5, RULE_basic = 6, RULE_stmts = 7, RULE_stmt = 8, RULE_loc = 9, 
		RULE_bool = 10, RULE_join = 11, RULE_equality = 12, RULE_rel = 13, RULE_expr = 14, 
		RULE_term = 15, RULE_unary = 16, RULE_factor = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "block", "decls", "decl", "id_list", "type_rule", "basic", 
			"stmts", "stmt", "loc", "bool", "join", "equality", "rel", "expr", "term", 
			"unary", "factor"
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

	@Override
	public String getGrammarFileName() { return "DragonLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public DragonLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DragonLangParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{

			          initializeGlobalScope();
			          System.out.println("program -> block");
			      
			setState(37);
			block();
			setState(38);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(DragonLangParser.LBRACE, 0); }
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public StmtsContext stmts() {
			return getRuleContext(StmtsContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(DragonLangParser.RBRACE, 0); }
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(LBRACE);

			            System.out.println("block -> { decls stmts }");

			            boolean didPush;
			            if (blockDepth == 0) {
			                // Outermost block of program: use existing global Env
			                didPush = false;
			            } else {
			                // Nested block: create a new Env
			                enterScope();
			                didPush = true;
			            }
			            blockDepth++;
			            blockPushed.push(Boolean.valueOf(didPush));
			        
			setState(42);
			decls();
			setState(43);
			stmts();
			setState(44);
			match(RBRACE);

			            blockDepth--;
			            boolean didPushExit = blockPushed.pop().booleanValue();
			            if (didPushExit) {
			                exitScope();
			            }
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclsContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decls);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << BOOLEAN))) != 0)) {
				{
				{
				setState(47);
				decl();
				 System.out.println("decls -> decls decl"); 
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("decls -> epsilon"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public Type_ruleContext t;
		public Id_listContext id_list() {
			return getRuleContext(Id_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DragonLangParser.SEMICOLON, 0); }
		public Type_ruleContext type_rule() {
			return getRuleContext(Type_ruleContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			((DeclContext)_localctx).t = type_rule();
			setState(58);
			id_list(((DeclContext)_localctx).t.val);
			setState(59);
			match(SEMICOLON);

			            System.out.println("decl -> type id_list ;");
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Id_listContext extends ParserRuleContext {
		public Type t;
		public Token ID;
		public List<TerminalNode> ID() { return getTokens(DragonLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DragonLangParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DragonLangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DragonLangParser.COMMA, i);
		}
		public Id_listContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Id_listContext(ParserRuleContext parent, int invokingState, Type t) {
			super(parent, invokingState);
			this.t = t;
		}
		@Override public int getRuleIndex() { return RULE_id_list; }
	}

	public final Id_listContext id_list(Type t) throws RecognitionException {
		Id_listContext _localctx = new Id_listContext(_ctx, getState(), t);
		enterRule(_localctx, 8, RULE_id_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			((Id_listContext)_localctx).ID = match(ID);
			 addIdentifier((((Id_listContext)_localctx).ID!=null?((Id_listContext)_localctx).ID.getText():null), _localctx.t); 
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(64);
				match(COMMA);
				setState(65);
				((Id_listContext)_localctx).ID = match(ID);
				 addIdentifier((((Id_listContext)_localctx).ID!=null?((Id_listContext)_localctx).ID.getText():null), _localctx.t); 
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_ruleContext extends ParserRuleContext {
		public Type val;
		public BasicContext b;
		public Token NUM;
		public BasicContext basic() {
			return getRuleContext(BasicContext.class,0);
		}
		public List<TerminalNode> LBRACKET() { return getTokens(DragonLangParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(DragonLangParser.LBRACKET, i);
		}
		public List<TerminalNode> NUM() { return getTokens(DragonLangParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(DragonLangParser.NUM, i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(DragonLangParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(DragonLangParser.RBRACKET, i);
		}
		public Type_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_rule; }
	}

	public final Type_ruleContext type_rule() throws RecognitionException {
		Type_ruleContext _localctx = new Type_ruleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type_rule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			((Type_ruleContext)_localctx).b = basic();

			            ((Type_ruleContext)_localctx).val =  makeBase((((Type_ruleContext)_localctx).b!=null?_input.getText(((Type_ruleContext)_localctx).b.start,((Type_ruleContext)_localctx).b.stop):null));
			        
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(74);
				match(LBRACKET);
				setState(75);
				((Type_ruleContext)_localctx).NUM = match(NUM);
				setState(76);
				match(RBRACKET);

				                _localctx.val.addDimension(Integer.parseInt((((Type_ruleContext)_localctx).NUM!=null?((Type_ruleContext)_localctx).NUM.getText():null)));
				            
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BasicContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(DragonLangParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(DragonLangParser.FLOAT, 0); }
		public TerminalNode BOOLEAN() { return getToken(DragonLangParser.BOOLEAN, 0); }
		public BasicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basic; }
	}

	public final BasicContext basic() throws RecognitionException {
		BasicContext _localctx = new BasicContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_basic);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << BOOLEAN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtsContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public StmtsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmts; }
	}

	public final StmtsContext stmts() throws RecognitionException {
		StmtsContext _localctx = new StmtsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stmts);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << DO) | (1L << BREAK) | (1L << LBRACE) | (1L << ID))) != 0)) {
				{
				{
				setState(85);
				stmt();
				 System.out.println("stmts -> stmts stmt"); 
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("stmts -> epsilon"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public LocContext loc() {
			return getRuleContext(LocContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(DragonLangParser.ASSIGN, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DragonLangParser.SEMICOLON, 0); }
		public TerminalNode IF() { return getToken(DragonLangParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(DragonLangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DragonLangParser.RPAREN, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(DragonLangParser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(DragonLangParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(DragonLangParser.DO, 0); }
		public TerminalNode BREAK() { return getToken(DragonLangParser.BREAK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stmt);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				loc();
				setState(96);
				match(ASSIGN);
				setState(97);
				bool();
				setState(98);
				match(SEMICOLON);
				 System.out.println("stmt -> loc = bool ;"); 
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				match(IF);
				setState(102);
				match(LPAREN);
				setState(103);
				bool();
				setState(104);
				match(RPAREN);
				setState(105);
				stmt();
				setState(111);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(106);
					match(ELSE);
					setState(107);
					stmt();
					 System.out.println("stmt -> if ( bool ) stmt else stmt"); 
					}
					break;
				case 2:
					{
					 System.out.println("stmt -> if ( bool ) stmt"); 
					}
					break;
				}
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(113);
				match(WHILE);
				setState(114);
				match(LPAREN);
				setState(115);
				bool();
				setState(116);
				match(RPAREN);
				setState(117);
				stmt();
				 System.out.println("stmt -> while ( bool ) stmt"); 
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 4);
				{
				setState(120);
				match(DO);
				setState(121);
				stmt();
				setState(122);
				match(WHILE);
				setState(123);
				match(LPAREN);
				setState(124);
				bool();
				setState(125);
				match(RPAREN);
				setState(126);
				match(SEMICOLON);
				 System.out.println("stmt -> do stmt while ( bool ) ;"); 
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 5);
				{
				setState(129);
				match(BREAK);
				setState(130);
				match(SEMICOLON);
				 System.out.println("stmt -> break ;"); 
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 6);
				{
				setState(132);
				block();
				 System.out.println("stmt -> block"); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocContext extends ParserRuleContext {
		public Token ID;
		public TerminalNode ID() { return getToken(DragonLangParser.ID, 0); }
		public List<TerminalNode> LBRACKET() { return getTokens(DragonLangParser.LBRACKET); }
		public TerminalNode LBRACKET(int i) {
			return getToken(DragonLangParser.LBRACKET, i);
		}
		public List<BoolContext> bool() {
			return getRuleContexts(BoolContext.class);
		}
		public BoolContext bool(int i) {
			return getRuleContext(BoolContext.class,i);
		}
		public List<TerminalNode> RBRACKET() { return getTokens(DragonLangParser.RBRACKET); }
		public TerminalNode RBRACKET(int i) {
			return getToken(DragonLangParser.RBRACKET, i);
		}
		public LocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loc; }
	}

	public final LocContext loc() throws RecognitionException {
		LocContext _localctx = new LocContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_loc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			((LocContext)_localctx).ID = match(ID);

			            noteLookup((((LocContext)_localctx).ID!=null?((LocContext)_localctx).ID.getText():null));
			        
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(139);
				match(LBRACKET);
				setState(140);
				bool();
				setState(141);
				match(RBRACKET);
				 System.out.println("loc -> loc [ bool ]"); 
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("loc -> id"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolContext extends ParserRuleContext {
		public List<JoinContext> join() {
			return getRuleContexts(JoinContext.class);
		}
		public JoinContext join(int i) {
			return getRuleContext(JoinContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(DragonLangParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(DragonLangParser.OR, i);
		}
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			join();
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(152);
				match(OR);
				setState(153);
				join();
				 System.out.println("bool -> bool || join"); 
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("bool -> join"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinContext extends ParserRuleContext {
		public List<EqualityContext> equality() {
			return getRuleContexts(EqualityContext.class);
		}
		public EqualityContext equality(int i) {
			return getRuleContext(EqualityContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(DragonLangParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(DragonLangParser.AND, i);
		}
		public JoinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join; }
	}

	public final JoinContext join() throws RecognitionException {
		JoinContext _localctx = new JoinContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_join);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			equality();
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(164);
				match(AND);
				setState(165);
				equality();
				 System.out.println("join -> join && equality"); 
				}
				}
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("join -> equality"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityContext extends ParserRuleContext {
		public List<RelContext> rel() {
			return getRuleContexts(RelContext.class);
		}
		public RelContext rel(int i) {
			return getRuleContext(RelContext.class,i);
		}
		public List<TerminalNode> EQ() { return getTokens(DragonLangParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(DragonLangParser.EQ, i);
		}
		public List<TerminalNode> NE() { return getTokens(DragonLangParser.NE); }
		public TerminalNode NE(int i) {
			return getToken(DragonLangParser.NE, i);
		}
		public EqualityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality; }
	}

	public final EqualityContext equality() throws RecognitionException {
		EqualityContext _localctx = new EqualityContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_equality);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			rel();
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQ || _la==NE) {
				{
				setState(184);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case EQ:
					{
					setState(176);
					match(EQ);
					setState(177);
					rel();
					 System.out.println("equality -> equality == rel"); 
					}
					break;
				case NE:
					{
					setState(180);
					match(NE);
					setState(181);
					rel();
					 System.out.println("equality -> equality != rel"); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("equality -> rel"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LT() { return getToken(DragonLangParser.LT, 0); }
		public TerminalNode LE() { return getToken(DragonLangParser.LE, 0); }
		public TerminalNode GE() { return getToken(DragonLangParser.GE, 0); }
		public TerminalNode GT() { return getToken(DragonLangParser.GT, 0); }
		public RelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel; }
	}

	public final RelContext rel() throws RecognitionException {
		RelContext _localctx = new RelContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_rel);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			expr();
			setState(208);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				{
				setState(192);
				match(LT);
				setState(193);
				expr();
				 System.out.println("rel -> expr < expr"); 
				}
				break;
			case LE:
				{
				setState(196);
				match(LE);
				setState(197);
				expr();
				 System.out.println("rel -> expr <= expr"); 
				}
				break;
			case GE:
				{
				setState(200);
				match(GE);
				setState(201);
				expr();
				 System.out.println("rel -> expr >= expr"); 
				}
				break;
			case GT:
				{
				setState(204);
				match(GT);
				setState(205);
				expr();
				 System.out.println("rel -> expr > expr"); 
				}
				break;
			case EQ:
			case NE:
			case AND:
			case OR:
			case SEMICOLON:
			case RPAREN:
			case RBRACKET:
				break;
			default:
				break;
			}
			 System.out.println("rel -> expr"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(DragonLangParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(DragonLangParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(DragonLangParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(DragonLangParser.MINUS, i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			term();
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				setState(221);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PLUS:
					{
					setState(213);
					match(PLUS);
					setState(214);
					term();
					 System.out.println("expr -> expr + term"); 
					}
					break;
				case MINUS:
					{
					setState(217);
					match(MINUS);
					setState(218);
					term();
					 System.out.println("expr -> expr - term"); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("expr -> term"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public List<UnaryContext> unary() {
			return getRuleContexts(UnaryContext.class);
		}
		public UnaryContext unary(int i) {
			return getRuleContext(UnaryContext.class,i);
		}
		public List<TerminalNode> TIMES() { return getTokens(DragonLangParser.TIMES); }
		public TerminalNode TIMES(int i) {
			return getToken(DragonLangParser.TIMES, i);
		}
		public List<TerminalNode> DIVIDE() { return getTokens(DragonLangParser.DIVIDE); }
		public TerminalNode DIVIDE(int i) {
			return getToken(DragonLangParser.DIVIDE, i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			unary();
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TIMES || _la==DIVIDE) {
				{
				setState(237);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TIMES:
					{
					setState(229);
					match(TIMES);
					setState(230);
					unary();
					 System.out.println("term -> term * unary"); 
					}
					break;
				case DIVIDE:
					{
					setState(233);
					match(DIVIDE);
					setState(234);
					unary();
					 System.out.println("term -> term / unary"); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("term -> unary"); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(DragonLangParser.NOT, 0); }
		public UnaryContext unary() {
			return getRuleContext(UnaryContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(DragonLangParser.MINUS, 0); }
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public UnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary; }
	}

	public final UnaryContext unary() throws RecognitionException {
		UnaryContext _localctx = new UnaryContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_unary);
		try {
			setState(255);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				match(NOT);
				setState(245);
				unary();
				 System.out.println("unary -> ! unary"); 
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(248);
				match(MINUS);
				setState(249);
				unary();
				 System.out.println("unary -> - unary"); 
				}
				break;
			case TRUE:
			case FALSE:
			case LPAREN:
			case NUM:
			case REAL:
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(252);
				factor();
				 System.out.println("unary -> factor"); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DragonLangParser.LPAREN, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DragonLangParser.RPAREN, 0); }
		public LocContext loc() {
			return getRuleContext(LocContext.class,0);
		}
		public TerminalNode NUM() { return getToken(DragonLangParser.NUM, 0); }
		public TerminalNode REAL() { return getToken(DragonLangParser.REAL, 0); }
		public TerminalNode TRUE() { return getToken(DragonLangParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(DragonLangParser.FALSE, 0); }
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_factor);
		try {
			setState(273);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(257);
				match(LPAREN);
				setState(258);
				bool();
				setState(259);
				match(RPAREN);
				 System.out.println("factor -> ( bool )"); 
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				loc();
				 System.out.println("factor -> loc"); 
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				match(NUM);
				 System.out.println("factor -> num"); 
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(267);
				match(REAL);
				 System.out.println("factor -> real"); 
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 5);
				{
				setState(269);
				match(TRUE);
				 System.out.println("factor -> true"); 
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 6);
				{
				setState(271);
				match(FALSE);
				 System.out.println("factor -> false"); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u0116\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4"+
		"\65\n\4\f\4\16\48\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\7\6F\n\6\f\6\16\6I\13\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7Q\n\7\f\7\16\7T\13"+
		"\7\3\b\3\b\3\t\3\t\3\t\7\t[\n\t\f\t\16\t^\13\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nr\n\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\5\n\u008a\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u0093\n\13"+
		"\f\13\16\13\u0096\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\7\f\u009f\n\f\f"+
		"\f\16\f\u00a2\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\7\r\u00ab\n\r\f\r\16\r"+
		"\u00ae\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16"+
		"\u00bb\n\16\f\16\16\16\u00be\13\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00d3"+
		"\n\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00e0"+
		"\n\20\f\20\16\20\u00e3\13\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\7\21\u00f0\n\21\f\21\16\21\u00f3\13\21\3\21\3\21\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0102\n\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\5\23\u0114\n\23\3\23\2\2\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$\2\3\3\2\n\f\2\u0121\2&\3\2\2\2\4*\3\2\2\2\6\66\3\2\2\2\b;\3\2"+
		"\2\2\n@\3\2\2\2\fJ\3\2\2\2\16U\3\2\2\2\20\\\3\2\2\2\22\u0089\3\2\2\2\24"+
		"\u008b\3\2\2\2\26\u0099\3\2\2\2\30\u00a5\3\2\2\2\32\u00b1\3\2\2\2\34\u00c1"+
		"\3\2\2\2\36\u00d6\3\2\2\2 \u00e6\3\2\2\2\"\u0101\3\2\2\2$\u0113\3\2\2"+
		"\2&\'\b\2\1\2\'(\5\4\3\2()\7\2\2\3)\3\3\2\2\2*+\7\37\2\2+,\b\3\1\2,-\5"+
		"\6\4\2-.\5\20\t\2./\7 \2\2/\60\b\3\1\2\60\5\3\2\2\2\61\62\5\b\5\2\62\63"+
		"\b\4\1\2\63\65\3\2\2\2\64\61\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\66\67\3"+
		"\2\2\2\679\3\2\2\28\66\3\2\2\29:\b\4\1\2:\7\3\2\2\2;<\5\f\7\2<=\5\n\6"+
		"\2=>\7\33\2\2>?\b\5\1\2?\t\3\2\2\2@A\7%\2\2AG\b\6\1\2BC\7\34\2\2CD\7%"+
		"\2\2DF\b\6\1\2EB\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\13\3\2\2\2IG\3"+
		"\2\2\2JK\5\16\b\2KR\b\7\1\2LM\7!\2\2MN\7#\2\2NO\7\"\2\2OQ\b\7\1\2PL\3"+
		"\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2S\r\3\2\2\2TR\3\2\2\2UV\t\2\2\2V\17"+
		"\3\2\2\2WX\5\22\n\2XY\b\t\1\2Y[\3\2\2\2ZW\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2"+
		"\\]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\b\t\1\2`\21\3\2\2\2ab\5\24\13\2bc\7"+
		"\r\2\2cd\5\26\f\2de\7\33\2\2ef\b\n\1\2f\u008a\3\2\2\2gh\7\3\2\2hi\7\35"+
		"\2\2ij\5\26\f\2jk\7\36\2\2kq\5\22\n\2lm\7\4\2\2mn\5\22\n\2no\b\n\1\2o"+
		"r\3\2\2\2pr\b\n\1\2ql\3\2\2\2qp\3\2\2\2r\u008a\3\2\2\2st\7\5\2\2tu\7\35"+
		"\2\2uv\5\26\f\2vw\7\36\2\2wx\5\22\n\2xy\b\n\1\2y\u008a\3\2\2\2z{\7\6\2"+
		"\2{|\5\22\n\2|}\7\5\2\2}~\7\35\2\2~\177\5\26\f\2\177\u0080\7\36\2\2\u0080"+
		"\u0081\7\33\2\2\u0081\u0082\b\n\1\2\u0082\u008a\3\2\2\2\u0083\u0084\7"+
		"\7\2\2\u0084\u0085\7\33\2\2\u0085\u008a\b\n\1\2\u0086\u0087\5\4\3\2\u0087"+
		"\u0088\b\n\1\2\u0088\u008a\3\2\2\2\u0089a\3\2\2\2\u0089g\3\2\2\2\u0089"+
		"s\3\2\2\2\u0089z\3\2\2\2\u0089\u0083\3\2\2\2\u0089\u0086\3\2\2\2\u008a"+
		"\23\3\2\2\2\u008b\u008c\7%\2\2\u008c\u0094\b\13\1\2\u008d\u008e\7!\2\2"+
		"\u008e\u008f\5\26\f\2\u008f\u0090\7\"\2\2\u0090\u0091\b\13\1\2\u0091\u0093"+
		"\3\2\2\2\u0092\u008d\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u0098\b\13"+
		"\1\2\u0098\25\3\2\2\2\u0099\u00a0\5\30\r\2\u009a\u009b\7\25\2\2\u009b"+
		"\u009c\5\30\r\2\u009c\u009d\b\f\1\2\u009d\u009f\3\2\2\2\u009e\u009a\3"+
		"\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1"+
		"\u00a3\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a4\b\f\1\2\u00a4\27\3\2\2"+
		"\2\u00a5\u00ac\5\32\16\2\u00a6\u00a7\7\24\2\2\u00a7\u00a8\5\32\16\2\u00a8"+
		"\u00a9\b\r\1\2\u00a9\u00ab\3\2\2\2\u00aa\u00a6\3\2\2\2\u00ab\u00ae\3\2"+
		"\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00af\3\2\2\2\u00ae"+
		"\u00ac\3\2\2\2\u00af\u00b0\b\r\1\2\u00b0\31\3\2\2\2\u00b1\u00bc\5\34\17"+
		"\2\u00b2\u00b3\7\22\2\2\u00b3\u00b4\5\34\17\2\u00b4\u00b5\b\16\1\2\u00b5"+
		"\u00bb\3\2\2\2\u00b6\u00b7\7\23\2\2\u00b7\u00b8\5\34\17\2\u00b8\u00b9"+
		"\b\16\1\2\u00b9\u00bb\3\2\2\2\u00ba\u00b2\3\2\2\2\u00ba\u00b6\3\2\2\2"+
		"\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bf"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c0\b\16\1\2\u00c0\33\3\2\2\2\u00c1"+
		"\u00d2\5\36\20\2\u00c2\u00c3\7\16\2\2\u00c3\u00c4\5\36\20\2\u00c4\u00c5"+
		"\b\17\1\2\u00c5\u00d3\3\2\2\2\u00c6\u00c7\7\20\2\2\u00c7\u00c8\5\36\20"+
		"\2\u00c8\u00c9\b\17\1\2\u00c9\u00d3\3\2\2\2\u00ca\u00cb\7\21\2\2\u00cb"+
		"\u00cc\5\36\20\2\u00cc\u00cd\b\17\1\2\u00cd\u00d3\3\2\2\2\u00ce\u00cf"+
		"\7\17\2\2\u00cf\u00d0\5\36\20\2\u00d0\u00d1\b\17\1\2\u00d1\u00d3\3\2\2"+
		"\2\u00d2\u00c2\3\2\2\2\u00d2\u00c6\3\2\2\2\u00d2\u00ca\3\2\2\2\u00d2\u00ce"+
		"\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\b\17\1\2"+
		"\u00d5\35\3\2\2\2\u00d6\u00e1\5 \21\2\u00d7\u00d8\7\27\2\2\u00d8\u00d9"+
		"\5 \21\2\u00d9\u00da\b\20\1\2\u00da\u00e0\3\2\2\2\u00db\u00dc\7\30\2\2"+
		"\u00dc\u00dd\5 \21\2\u00dd\u00de\b\20\1\2\u00de\u00e0\3\2\2\2\u00df\u00d7"+
		"\3\2\2\2\u00df\u00db\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1"+
		"\u00e2\3\2\2\2\u00e2\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\b\20"+
		"\1\2\u00e5\37\3\2\2\2\u00e6\u00f1\5\"\22\2\u00e7\u00e8\7\31\2\2\u00e8"+
		"\u00e9\5\"\22\2\u00e9\u00ea\b\21\1\2\u00ea\u00f0\3\2\2\2\u00eb\u00ec\7"+
		"\32\2\2\u00ec\u00ed\5\"\22\2\u00ed\u00ee\b\21\1\2\u00ee\u00f0\3\2\2\2"+
		"\u00ef\u00e7\3\2\2\2\u00ef\u00eb\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef"+
		"\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4"+
		"\u00f5\b\21\1\2\u00f5!\3\2\2\2\u00f6\u00f7\7\26\2\2\u00f7\u00f8\5\"\22"+
		"\2\u00f8\u00f9\b\22\1\2\u00f9\u0102\3\2\2\2\u00fa\u00fb\7\30\2\2\u00fb"+
		"\u00fc\5\"\22\2\u00fc\u00fd\b\22\1\2\u00fd\u0102\3\2\2\2\u00fe\u00ff\5"+
		"$\23\2\u00ff\u0100\b\22\1\2\u0100\u0102\3\2\2\2\u0101\u00f6\3\2\2\2\u0101"+
		"\u00fa\3\2\2\2\u0101\u00fe\3\2\2\2\u0102#\3\2\2\2\u0103\u0104\7\35\2\2"+
		"\u0104\u0105\5\26\f\2\u0105\u0106\7\36\2\2\u0106\u0107\b\23\1\2\u0107"+
		"\u0114\3\2\2\2\u0108\u0109\5\24\13\2\u0109\u010a\b\23\1\2\u010a\u0114"+
		"\3\2\2\2\u010b\u010c\7#\2\2\u010c\u0114\b\23\1\2\u010d\u010e\7$\2\2\u010e"+
		"\u0114\b\23\1\2\u010f\u0110\7\b\2\2\u0110\u0114\b\23\1\2\u0111\u0112\7"+
		"\t\2\2\u0112\u0114\b\23\1\2\u0113\u0103\3\2\2\2\u0113\u0108\3\2\2\2\u0113"+
		"\u010b\3\2\2\2\u0113\u010d\3\2\2\2\u0113\u010f\3\2\2\2\u0113\u0111\3\2"+
		"\2\2\u0114%\3\2\2\2\24\66GR\\q\u0089\u0094\u00a0\u00ac\u00ba\u00bc\u00d2"+
		"\u00df\u00e1\u00ef\u00f1\u0101\u0113";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}