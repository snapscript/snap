package org.snapscript.parse;

public class NumberToken implements Token<Number> {
   
   private final Number value;
   private final Line line;
   private final int type;

   public NumberToken(Number value) {
      this(value, null, 0);
   }
   
   public NumberToken(Number value, Line line, int type) {
      this.value = value;
      this.type = type;
      this.line = line;
   }
   
   @Override
   public Number getValue() {
      return value;
   }
   
   @Override
   public Line getLine(){ 
      return line;
   }
   
   @Override
   public int getType() {
      return type;
   }
}
