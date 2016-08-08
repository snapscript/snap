package org.snapscript.parse;

public interface LexicalAnalyzer {      
   Token<String> type();   
   Token<String> text();
   Token<String> template();
   Token<String> identifier();
   Token<String> qualifier();
   Token<String> literal(String text);
   Token<Number> hexidecimal();
   Token<Number> binary();
   Token<Number> decimal();
   Line line(int mark);
   int reset(int mark);
   int count();
   int mark();  
}
