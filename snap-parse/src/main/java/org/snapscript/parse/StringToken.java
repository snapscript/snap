package org.snapscript.parse;

public class StringToken implements Token<String>{
   
   private final String value;
   private final Line line;
   private final int type;
   
   public StringToken(String value) {
      this(value, null, 0);
   }
   
   public StringToken(String value, Line line, int type) {
      this.value = value;
      this.type = type;
      this.line = line;
   }
   
   @Override
   public String getValue() {
      return value;
   }
   
   @Override
   public Line getLine() {
      return line;
   }
   
   @Override
   public int getType() {
      return type;
   }
}
