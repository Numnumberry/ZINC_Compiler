Program: Lexer.jflex Parser.y
	./byaccj -J Parser.y
	jflex Lexer.jflex
	javac Parser.java
	javac Lexer.java
