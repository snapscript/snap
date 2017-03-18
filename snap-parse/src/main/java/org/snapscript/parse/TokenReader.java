
package org.snapscript.parse;

public interface TokenReader {
   boolean literal(String value);   
   boolean decimal();
   boolean binary();
   boolean hexidecimal();
   boolean identifier();
   boolean qualifier();
   boolean template();
   boolean text();
   boolean type(); 
}
