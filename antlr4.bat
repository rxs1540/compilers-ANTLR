@echo off
rem Adjust JAR path if you moved the jar
set JAR=C:\JavaLibAntlr\antlr-4.7.2-complete.jar

rem Run the ANTLR Tool
java -Xmx500M -cp "%JAR%" org.antlr.v4.Tool %*
