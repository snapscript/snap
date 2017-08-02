package org.snapscript.tree.template;

public class SegmentIterator {
   
   private static final short[] IDENTIFIER = {
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 
   0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 
   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
   1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
   
   private char[] source;
   private int off;
   
   public SegmentIterator(String text) {
      this.source = text.toCharArray();
   }
   
   public Segment next() {
      int mark = off;
      
      while(off < source.length){
         char next = source[off];

         if(next == '$') {
            if(off > mark) {
               return new TextSegment(source, mark, off - mark);
            }
         } else if(off > 0) {
            char prev = source[off - 1];
            
            if(next == '{' && prev == '$') {
               int start = off + 1;
               int special = 0;
               
               while(off < source.length) {
                  char symbol = source[off++];
                  
                  if(symbol == '}') {
                     if(special > 0) {
                        return new ExpressionSegment(source, mark, off - mark);
                     }
                     return new VariableSegment(source, mark, off - mark);
                  } 
                  if(off > start) {
                     if(IDENTIFIER.length < symbol) {
                        special++;
                     } else if(IDENTIFIER[symbol] == 0) {
                        special++;
                     }
                  }
               }
               return new TextSegment(source, mark, off - mark);
            }
         }
         off++;
      }
      if(off > mark) {
         return new TextSegment(source, mark, off - mark);
      }
      return null;
   } 
   
   public boolean hasNext() {
      return off < source.length;
   }
}