%%
%class Lexer
%unicode
%byaccj
%standalone
white = [ \t]+

%{
int lineCount = 1;
private Parser yyparser;
public Lexer(java.io.Reader r, Parser yyparser) {
	this(r);
	this.yyparser = yyparser;
}
public int getLine() {
	return lineCount;
}
%}

%%
":="				 	{return Parser.ASGN;}
";"					 	{return Parser.SC;}
":"					 	{return Parser.COLON;}
"**"				 	{return Parser.POWER;}
"+"					 	{yyparser.yylval = new ParserVal("ADD"); return Parser.ADDITIVE;}
"-"					 	{yyparser.yylval = new ParserVal("SUB"); return Parser.ADDITIVE;}
"*"					 	{yyparser.yylval = new ParserVal("MPY"); return Parser.MULTIPLICATIVE;}
div					 	{yyparser.yylval = new ParserVal("DIV"); return Parser.MULTIPLICATIVE;}
mod					 	{yyparser.yylval = new ParserVal("MOD"); return Parser.MULTIPLICATIVE;}
"="					 	{yyparser.yylval = new ParserVal("EQ"); return Parser.COMPARE;}
"<>"				 	{yyparser.yylval = new ParserVal("NE"); return Parser.COMPARE;}
"<"					 	{yyparser.yylval = new ParserVal("LT"); return Parser.COMPARE;}
">"					 	{yyparser.yylval = new ParserVal("GT"); return Parser.COMPARE;}
"<="				 	{yyparser.yylval = new ParserVal("LE"); return Parser.COMPARE;}
">="				 	{yyparser.yylval = new ParserVal("GE"); return Parser.COMPARE;}
"("					 	{return Parser.LP;}
")"						{return Parser.RP;}
if						{return Parser.IF;}
then					{return Parser.THEN;}
else					{return Parser.ELSE;}
begin					{return Parser.BEGIN;}
program				 	{return Parser.PROGRAM;}
end					 	{return Parser.END;}
endif					{return Parser.ENDIF;}
endwhile				{return Parser.ENDWHILE;}
while					{return Parser.WHILE;}
loop					{return Parser.LOOP;}
var						{return Parser.VAR;}
integer					{return Parser.INT;}
writeInt				{return Parser.WRITEINT;}
readInt					{return Parser.READINT;}
("+"|"-")?[1-9][0-9]*|0	{yyparser.yylval = new ParserVal(Integer.parseInt(yytext())); return Parser.num;}
[A-Z][_A-Z0-9]*			{yyparser.yylval = new ParserVal(yytext()); return Parser.ident;}
{white}				 	{/*nothing*/}
\n					 	{++lineCount;}
"--"(.)*\n				{++lineCount;}
.					 	{System.out.println("Error: line "+lineCount+": Unknown Token \""+yytext()+"\"");
						 System.exit(0);}
