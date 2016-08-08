package org.snapscript.parse;

public interface SyntaxBuilder {
   SyntaxBuilder mark(int index); 
   boolean literal(String value);   
   boolean decimal();
   boolean binary();
   boolean hexidecimal();
   boolean identifier();
   boolean qualifier();
   boolean template();
   boolean text();
   boolean type();  
   int position(); 
   void commit();
   int reset();
}