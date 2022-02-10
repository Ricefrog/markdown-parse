CLASSPATH=lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:.

MarkdownParse.class: MarkdownParse.java
	javac -cp $(CLASSPATH) MarkdownParse.java

MarkdownParseTest.class: MarkdownParseTest.java MarkdownParse.java
	javac -cp $(CLASSPATH) MarkdownParse.java MarkdownParseTest.java

test: MarkdownParse.class MarkdownParseTest.class
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore MarkdownParseTest
