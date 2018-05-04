//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Parser.y"
import java.io.*;
import java.util.Stack;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASGN=257;
public final static short SC=258;
public final static short COLON=259;
public final static short POWER=260;
public final static short ADDITIVE=261;
public final static short MULTIPLICATIVE=262;
public final static short COMPARE=263;
public final static short LP=264;
public final static short RP=265;
public final static short IF=266;
public final static short THEN=267;
public final static short ELSE=268;
public final static short BEGIN=269;
public final static short PROGRAM=270;
public final static short END=271;
public final static short ENDIF=272;
public final static short ENDWHILE=273;
public final static short WHILE=274;
public final static short LOOP=275;
public final static short VAR=276;
public final static short INT=277;
public final static short WRITEINT=278;
public final static short READINT=279;
public final static short num=280;
public final static short ident=281;
public final static short ADD=282;
public final static short SUB=283;
public final static short MUL=284;
public final static short DIV=285;
public final static short NEG=286;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    2,    4,    0,    1,    6,    1,    5,    3,    3,    7,
    7,    7,    7,    7,   13,    8,    8,   14,    9,   15,
   16,   15,   17,   18,   19,   10,   11,   12,   12,   20,
   20,   21,   21,   22,   22,   23,   23,   23,
};
final static short yylen[] = {                            2,
    0,    0,    7,    0,    0,    7,    1,    0,    3,    0,
    1,    1,    1,    1,    0,    4,    3,    0,    7,    0,
    0,    3,    0,    0,    0,    8,    2,    3,    1,    3,
    1,    3,    1,    3,    1,    1,    1,    3,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,    0,    0,    2,    5,    0,    0,
    0,   23,    0,    0,    0,    0,   11,   12,   13,   14,
    7,    0,    0,   37,   36,    0,    0,    0,    0,   35,
    0,    0,    0,    3,    0,    0,    0,    0,   18,    0,
    0,    0,    0,   17,    0,    9,    6,   38,    0,    0,
    0,    0,   34,   24,    0,    0,    0,   21,    0,   25,
    0,   19,    0,   22,   26,
};
final static short yydgoto[] = {                          2,
    5,    3,   15,    9,   22,   10,   16,   17,   18,   19,
   20,   26,   45,   50,   59,   61,   31,   57,   63,   27,
   28,   29,   30,
};
final static short yysindex[] = {                      -258,
    0,    0, -262, -240, -226, -193,    0,    0, -201, -223,
 -251,    0, -251, -206, -202, -186,    0,    0,    0,    0,
    0, -175, -251,    0,    0, -259, -177, -176, -173,    0,
 -251, -178, -191,    0, -201, -262, -184, -251,    0, -251,
 -251, -251, -260,    0, -251,    0,    0,    0, -177, -201,
 -176, -173,    0,    0, -178, -179, -201,    0, -182,    0,
 -201,    0, -181,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0, -174,    0,    0,    0,    0,    0, -222,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -208, -230, -256,    0,
    0, -167, -217,    0, -197, -174,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -205, -190,
 -219, -241,    0,    0, -165, -172, -233,    0,    0,    0,
 -220,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   58,    0,  -34,    0,    0,    0,    0,    0,    0,    0,
    0,  -13,    0,    0,    0,    0,    0,    0,    0,   59,
   56,   57,   60,
};
final static int YYTABLESIZE=102;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   46,   33,   38,   38,   33,   33,   33,   39,   33,   37,
   33,    1,   23,    4,   54,   56,   32,   43,   33,   32,
   32,   32,   60,   32,   10,   32,   64,   31,   24,   25,
   31,   55,   31,   32,   31,   10,   31,   10,   30,    8,
    6,   30,    7,   30,   31,   30,   15,   30,    8,   29,
   33,    8,   28,   21,   29,   30,   29,   28,   29,   28,
   10,   28,   15,   15,   11,    8,   29,   10,   34,   28,
    8,   35,   12,    8,    8,    8,   13,    8,   38,   14,
   48,    8,   36,   40,   38,   41,   42,   44,   58,   62,
   27,   65,   16,   47,    4,   51,   49,   52,    0,   20,
    0,   53,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         13,
   35,  258,  263,  263,  261,  262,  263,  267,  265,   23,
  267,  270,  264,  276,  275,   50,  258,   31,  275,  261,
  262,  263,   57,  265,  258,  267,   61,  258,  280,  281,
  261,   45,  263,  275,  265,  258,  267,  258,  258,  273,
  281,  261,  269,  263,  275,  265,  264,  267,  271,  258,
  257,  272,  258,  277,  263,  275,  265,  263,  267,  265,
  258,  267,  280,  281,  266,  259,  275,  258,  271,  275,
  268,  258,  274,  271,  272,  273,  278,  268,  263,  281,
  265,  272,  258,  261,  263,  262,  260,  279,  268,  272,
  258,  273,  258,   36,  269,   40,   38,   41,   -1,  272,
   -1,   42,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=286;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASGN","SC","COLON","POWER","ADDITIVE","MULTIPLICATIVE",
"COMPARE","LP","RP","IF","THEN","ELSE","BEGIN","PROGRAM","END","ENDIF",
"ENDWHILE","WHILE","LOOP","VAR","INT","WRITEINT","READINT","num","ident","ADD",
"SUB","MUL","DIV","NEG",
};
final static String yyrule[] = {
"$accept : program",
"$$1 :",
"$$2 :",
"program : PROGRAM $$1 declarations BEGIN $$2 statementSequence END",
"declarations :",
"$$3 :",
"declarations : VAR ident COLON $$3 type SC declarations",
"type : INT",
"statementSequence :",
"statementSequence : statement SC statementSequence",
"statement :",
"statement : assignment",
"statement : ifStatement",
"statement : whileStatement",
"statement : writeInt",
"$$4 :",
"assignment : ident ASGN $$4 expression",
"assignment : ident ASGN READINT",
"$$5 :",
"ifStatement : IF expression THEN $$5 statementSequence elseClause ENDIF",
"elseClause :",
"$$6 :",
"elseClause : ELSE $$6 statementSequence",
"$$7 :",
"$$8 :",
"$$9 :",
"whileStatement : WHILE $$7 expression LOOP $$8 statementSequence $$9 ENDWHILE",
"writeInt : WRITEINT expression",
"expression : expression COMPARE simpleExpression",
"expression : simpleExpression",
"simpleExpression : simpleExpression ADDITIVE term",
"simpleExpression : term",
"term : term MULTIPLICATIVE factor",
"term : factor",
"factor : factor POWER primary",
"factor : primary",
"primary : ident",
"primary : num",
"primary : LP expression RP",
};

//#line 92 "Parser.y"

private Lexer lexer;
public static PrintWriter outputFile;				//declare outputFile
public static SymbolTable st;						//declare symbol table
public static int numLabel = 0;						//declare label counter
public static Stack<String> labelStack;				//declare stack for labels

public void pushPair(String lab1, String lab2) {
	labelStack.push(lab2);							//push the second label
	labelStack.push(lab1);							//push the first
	labelStack.push(lab2);							//push the second again
	labelStack.push(lab1);							//push the first again
}

public void checkIfDeclared(String ident) {
	if (st.lookup(ident) == false) {							//if identifier not in symbol table
		st.insert(ident, lexer.getLine());						//then insert it
	} else {													//if in the table
		System.out.print("Error: line "+lexer.getLine()+": ");	//then error
		System.out.println("\""+ident+"\" already declared");	//it is already declared
		System.exit(0);											//abandon ship
	}
}

public void rvalueCheck(String ident) {
	if (st.lookup(ident) == false) {							//if identifier not in symbol table
		System.out.print("Error: line "+lexer.getLine()+": ");	//then error
		System.out.println("\""+ident+"\" not declared");		//it has not been declared
		System.exit(0);											//abandon ship
	} else {													//if in table
		outputFile.println("\tRVALUE\t"+ident);					//then put its rvalue
	}
}

public void lvalueCheck(String ident) {
	if (st.lookup(ident) == false) {							//if identifier not in symbol table
		System.out.print("Error: line "+lexer.getLine()+": ");	//then error
		System.out.println("\""+ident+"\" not declared");		//it has not been declared
		System.exit(0);											//abandon ship
	} else {													//if in the table
		outputFile.println("\tLVALUE\t"+ident);					//then put its lvalue
	}
}	

public String genLabel() {
	numLabel++;					//update label counter
	return "Label"+numLabel;	//return the new String label
}

private int yylex() {
	int retVal = -1;
	try {
		retVal = lexer.yylex();
	} catch (IOException e) {
		System.err.println("IO Error:" + e);
	}
	return retVal;
}

public void yyerror(String error) {
	System.err.println("Error: line "+lexer.getLine()+": "+error);
}

public Parser(Reader r) {
	lexer = new Lexer(r, this);
}

public static void main(String[] args) throws IOException {	//initializes and calls yyparse()
	if (args.length == 0) {
		System.out.println("Enter file name as command line argument");
		System.exit(0);
	}
	Parser yyparser = new Parser(new FileReader(args[0]));
	outputFile = new PrintWriter("OUTPUT.asm");
	st = new SymbolTable();
	labelStack = new Stack<String>();
	yyparser.yyparse();
	outputFile.close();
}
//#line 350 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 37 "Parser.y"
{outputFile.println("Section .data");}
break;
case 2:
//#line 38 "Parser.y"
{outputFile.println("Section .code");}
break;
case 3:
//#line 40 "Parser.y"
{outputFile.println("\tHALT"); System.out.println("COMPILED: output in \"OUTPUT.asm\"");}
break;
case 5:
//#line 43 "Parser.y"
{checkIfDeclared(val_peek(1).sval); outputFile.println("\t"+val_peek(1).sval+":\tword");}
break;
case 15:
//#line 57 "Parser.y"
{lvalueCheck(val_peek(1).sval);}
break;
case 16:
//#line 58 "Parser.y"
{outputFile.println("\tSTO");}
break;
case 17:
//#line 59 "Parser.y"
{lvalueCheck(val_peek(2).sval); outputFile.println("\tREADINT"); outputFile.println("\tSTO");}
break;
case 18:
//#line 61 "Parser.y"
{pushPair(genLabel(), genLabel()); outputFile.println("\tGOFALSE\t"+labelStack.pop());}
break;
case 20:
//#line 64 "Parser.y"
{labelStack.pop(); outputFile.println("\tLABEL\t"+labelStack.pop()); labelStack.pop();}
break;
case 21:
//#line 65 "Parser.y"
{outputFile.println("\tGOTO\t"+labelStack.pop()); outputFile.println("\tLABEL\t"+labelStack.pop());}
break;
case 22:
//#line 66 "Parser.y"
{outputFile.println("\tLABEL\t"+labelStack.pop());}
break;
case 23:
//#line 68 "Parser.y"
{pushPair(genLabel(), genLabel()); outputFile.println("\tLABEL\t"+labelStack.pop());}
break;
case 24:
//#line 69 "Parser.y"
{outputFile.println("\tGOFALSE\t"+labelStack.pop());}
break;
case 25:
//#line 70 "Parser.y"
{outputFile.println("\tGOTO\t"+labelStack.pop());}
break;
case 26:
//#line 71 "Parser.y"
{outputFile.println("\tLABEL\t"+labelStack.pop());}
break;
case 27:
//#line 73 "Parser.y"
{outputFile.println("\tPRINT");}
break;
case 28:
//#line 75 "Parser.y"
{outputFile.println("\t"+val_peek(1).sval);}
break;
case 30:
//#line 78 "Parser.y"
{outputFile.println("\t"+val_peek(1).sval);}
break;
case 32:
//#line 81 "Parser.y"
{outputFile.println("\t"+val_peek(1).sval);}
break;
case 34:
//#line 84 "Parser.y"
{outputFile.println("\tPOW");}
break;
case 36:
//#line 87 "Parser.y"
{rvalueCheck(val_peek(0).sval);}
break;
case 37:
//#line 88 "Parser.y"
{outputFile.println("\tPUSH\t"+val_peek(0).ival);}
break;
//#line 587 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
