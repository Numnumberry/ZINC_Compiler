%{
import java.io.*;
import java.util.Stack;
%}

%token ASGN
%token SC
%token COLON
%token POWER
%token ADDITIVE
%token MULTIPLICATIVE
%token COMPARE
%token LP
%token RP
%token IF
%token THEN
%token ELSE
%token BEGIN
%token PROGRAM
%token END
%token ENDIF
%token ENDWHILE
%token WHILE
%token LOOP
%token VAR
%token INT
%token WRITEINT
%token READINT
%token num
%token ident
%left ADD SUB
%left MUL DIV
%left NEG		//unary minus
%right POWER

%%
program: 			PROGRAM 								{outputFile.println("Section .data");}		//start the data section
					declarations BEGIN						{outputFile.println("Section .code");}		//start the code section
					statementSequence
					END 									{outputFile.println("\tHALT"); System.out.println("COMPILED: output in \"OUTPUT.asm\"");}	//print this if make it to the end
		 			;
declarations:		/*epsilon*/
					|VAR ident COLON 						{checkIfDeclared($2.sval); outputFile.println("\t"+$2.sval+":\tword");}		//check if identifier already declared
					type SC declarations
					;
type:				INT
					;
statementSequence:	/*epsilon*/
					|statement SC statementSequence
					;
statement:			/*epsilon*/
					|assignment
					|ifStatement
					|whileStatement
					|writeInt
					;
assignment:			ident ASGN 								{lvalueCheck($1.sval);}			//check if left-hand identifier declared
					expression 								{outputFile.println("\tSTO");}	//store after expession finished
					|ident ASGN READINT						{lvalueCheck($1.sval); outputFile.println("\tREADINT"); outputFile.println("\tSTO");}	//check if left hand identifier declared, read then store
					;
ifStatement:		IF expression THEN						{pushPair(genLabel(), genLabel()); outputFile.println("\tGOFALSE\t"+labelStack.pop());}	//push a pair of labels on stack, then GOFALSE to label on top
					statementSequence elseClause ENDIF		 
					;
elseClause:			/*epsilon*/								{labelStack.pop(); outputFile.println("\tLABEL\t"+labelStack.pop()); labelStack.pop();} //if no else, pop off the extra labels and keep the one we need
					|ELSE									{outputFile.println("\tGOTO\t"+labelStack.pop()); outputFile.println("\tLABEL\t"+labelStack.pop());} //if else, pop the stack for GOTO and LABEL labels
					statementSequence						{outputFile.println("\tLABEL\t"+labelStack.pop());}		//after the statements, pop the stack for the last label
					;
whileStatement:		WHILE									{pushPair(genLabel(), genLabel()); outputFile.println("\tLABEL\t"+labelStack.pop());}	//push a pair of labels on stack, then pop for the first LABEL
					expression LOOP							{outputFile.println("\tGOFALSE\t"+labelStack.pop());}	//after expression, pop stack for GOFALSE label
					statementSequence						{outputFile.println("\tGOTO\t"+labelStack.pop());}		//after statements, pop stack for GOTO label
					ENDWHILE								{outputFile.println("\tLABEL\t"+labelStack.pop());}		//after while loop, pop stack for LABEL label
					;
writeInt:			WRITEINT expression						{outputFile.println("\tPRINT");}			//after expression, PRINT
					;
expression:			expression COMPARE simpleExpression		{outputFile.println("\t"+$2.sval);}			//after comparison expression, print the comparison instruction
					|simpleExpression
					;
simpleExpression:	simpleExpression ADDITIVE term 			{outputFile.println("\t"+$2.sval);}			//after additive expression, print the additive instruction
					|term
					;
term:				term MULTIPLICATIVE factor 				{outputFile.println("\t"+$2.sval);}			//after multiplicative expression, print the multiplicative instruction
					|factor
					;
factor:				factor POWER primary 					{outputFile.println("\tPOW");}				//print power instruction
					|primary
					;
primary:			ident									{rvalueCheck($1.sval);}						//check if right-hand identifier declared
					|num									{outputFile.println("\tPUSH\t"+$1.ival);}	//push instruction for number literals
					|LP expression RP
					;
%%

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
