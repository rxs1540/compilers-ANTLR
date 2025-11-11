@echo off
rem Adjust JAR path if you moved the jar
set JAR=C:\JavaLibAntlr\antlr-4.7.2-complete.jar

rem Run TestRig (grun) - prints tokens or runs parser
java -cp "%JAR%;." org.antlr.v4.gui.TestRig %*
