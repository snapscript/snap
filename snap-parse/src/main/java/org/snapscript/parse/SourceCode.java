package org.snapscript.parse;

public class SourceCode {
   
   private final char[] original;
   private final char[] source;
   private final short[] lines;
   private final short[] types;
   
   public SourceCode(char[] original, char[] source, short[] lines, short[] types) {
      this.original = original;
      this.source = source;
      this.lines = lines;
      this.types = types;
   }
   
   public char[] getOriginal() {
      return original;
   }
   
   public char[] getSource() {
      return source;
   }
   
   public short[] getLines() {
      return lines;
   }
   
   public short[] getTypes() {
      return types;
   }
   
   @Override
   public String toString() {
      return new String(source);
   }
}