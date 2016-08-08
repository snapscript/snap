package org.snapscript.parse;

public enum TokenType {
   IDENTIFIER(0, 0x0001),
   TYPE(1, 0x0002),   
   QUALIFIER(2, 0x0004),   
   HEXIDECIMAL(3, 0x0008),   
   BINARY(4, 0x0010),  
   DECIMAL(5, 0x0020),
   TEXT(6, 0x0040),
   LITERAL(7, 0x0080),
   TEMPLATE(8, 0x0100);
   
   public final int index;
   public final int mask;
   
   private TokenType(int index, int mask) {
      this.index = index;
      this.mask = mask;
   }
}
