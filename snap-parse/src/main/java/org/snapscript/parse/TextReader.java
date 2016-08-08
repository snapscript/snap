package org.snapscript.parse;

import static org.snapscript.parse.TextCategory.BINARY;
import static org.snapscript.parse.TextCategory.CAPITAL;
import static org.snapscript.parse.TextCategory.DIGIT;
import static org.snapscript.parse.TextCategory.DOLLAR;
import static org.snapscript.parse.TextCategory.DOUBLE;
import static org.snapscript.parse.TextCategory.ESCAPE;
import static org.snapscript.parse.TextCategory.FLOAT;
import static org.snapscript.parse.TextCategory.HEXIDECIMAL;
import static org.snapscript.parse.TextCategory.IDENTIFIER;
import static org.snapscript.parse.TextCategory.LETTER;
import static org.snapscript.parse.TextCategory.LONG;
import static org.snapscript.parse.TextCategory.MINUS;
import static org.snapscript.parse.TextCategory.PERIOD;
import static org.snapscript.parse.TextCategory.QUOTE;
import static org.snapscript.parse.TextCategory.SPECIAL;

public class TextReader {

   private NumberConverter converter;
   private TextDecoder decoder;
   private short[] types;
   private char[] source;
   private int count;
   private int off;

   public TextReader(char[] source, short[] types) {
      this(source, types, 0, source.length);
   }
   
   public TextReader(char[] source, short[] types, int off, int count) {
      this.decoder = new TextDecoder(source, off, count);
      this.converter = new NumberConverter();
      this.source = source;
      this.count = count;
      this.types = types;
      this.off = off;      
   }       
   
   public Character peek() {
      if(off < count) {
         return source[off];
      }
      return null;
   }
   
   public Character next() {
      if(off < count) {
         return source[off++];
      }
      return null;
   }
   
   public Character literal(char value) {
      if(off < count) {   
         char next = source[off];
         
         if(next == value) {
            off++;
            return next;
         }
      }
      return null;
   }
   
   public String literal(char[] text) {
      int length = text.length;      
      int mark = off;
      
      if(count - off >= length) {
         for(int i = 0; i < length; i++) {
            char value = text[i];
            char next = source[off + i];
            
            if(value != next) {
               return null;
            }
         }
         off += length;
         return decoder.decode(mark, length, false);
      }
      return null;
   }
   
   public String literal(String text) {
      int length = text.length();      
      
      if(count - off >= length) {
         for(int i = 0; i < length; i++) {
            char value = text.charAt(i);
            char next = source[off + i];
            
            if(value != next) {
               return null;
            }
         }
         off += length;
         return text;
      }
      return null;
   }
   
   public Character letter() {
      if(off < count) {
         short type = types[off];
         
         if((type & LETTER) == LETTER) {           
            return source[off++];
         }
      }
      return null;
   }
   
   
   public Character digit() {      
      if(off < count) {
         short type = types[off];
         
         if((type & DIGIT) == DIGIT) {           
            return source[off++];
         }
      }
      return null;
   }
   
   public Number binary() {  
      if(off + 3 < count) {
         char first = source[off];
         char second = source[off+1];
 
         if(first != '0') { 
            return null;
         } 
         if(second != 'b' && second != 'B') {
            return null;
         }
         Class type = int.class;
         int pos = off + 2;
         int mark = off;
         int value = 0;
         
         while(pos < count) {
            short mask = types[pos];
            
            if((mask & BINARY) == BINARY) {
               char next = source[pos];
               
               value <<= 1;
               value |= decoder.binary(next);
            } else {
               if((mask & LONG) == LONG) {
                  type = long.class;
                  off++;
               } 
               break;
            }
            pos++;
         }
         if(pos > mark + 2) {                  
            off = pos;
            return converter.convert(type, value);
         }
      }
      return null;      
   } 
   
   public Number hexidecimal() {  
      if(off + 3 < count) {
         char first = source[off];
         char second = source[off+1];
         
         if(first != '0') { 
            return null;
         } 
         if(second != 'x' && second != 'X') {
            return null;
         }
         Class type = int.class;
         int pos = off + 2;
         int mark = off;
         int value = 0;
         
         while(pos < count) {
            short mask = types[pos];
            
            if((mask & HEXIDECIMAL) == HEXIDECIMAL) {
               char next = source[pos];
               
               value <<= 4;
               value |= decoder.hexidecimal(next);
            } else {
               if((mask & LONG) == LONG) {
                  type = long.class;
                  off++;
               } 
               break;
            }
            pos++;
         }
         if(pos > mark + 2) {                  
            off = pos;
            return converter.convert(type, value);
         }
      }
      return null;      
   }    
   
   public Number decimal() {
      Class type = int.class;
      double scale = 0;
      long value = 0;
      int mark = off;
      int sign = 1;

      while (off < count) {
         char next = source[off];
         short mask = types[off]; 

         if ((mask & DIGIT) == 0) {
            if (off > mark) {
               if((mask & PERIOD) == PERIOD && scale == 0) {
                  if(off + 1 < count) {
                     mask = types[off + 1];
                     
                     if((mask & DIGIT) == DIGIT) {
                        type = double.class;
                        scale = 1.0d;
                        off++;
                        continue;
                     }
                  }
               } else if ((mask & FLOAT) == FLOAT) {
                  type = float.class;
                  off++; 
               } else if ((mask & DOUBLE) == DOUBLE) {
                  type = double.class;
                  off++;    
               } else if((mask & LONG) == LONG) {
                  type = long.class;
                  off++;
               }               
               break;
            } else {
               if((mask & MINUS) == MINUS){ 
                  if(off + 1 < count) {
                     mask = types[off + 1];
                     
                     if((mask & DIGIT) == DIGIT) {
                        sign = -1;
                        off++;
                        continue;
                     }
                  }
               }
               return null;
            }
         } else {
            value *= 10;
            value += next;
            value -= '0';
            scale *= 10.0d;
         }
         off++;
      }
      if (off > mark) {
         double result = sign * value;
         
         if(scale > 0) {
            result /= scale;
         }         
         return converter.convert(type, result);
      }
      return null;
   }   
   
   public String text() {
      int mark = off + 1;
      int pos = off + 1;
      
      if(pos < count) {
         char start = source[off];
         short mask = types[off];
         char next = start;
         
         if((mask & QUOTE) == QUOTE) {
            int escape = 0;
            int length = 0;
            
            while(pos < count) {
               next = source[pos++];
               
               if(next == start) {
                  off = pos;
                  break;
               }
               mask = types[pos -1];
               
               if((mask & ESCAPE) == ESCAPE){
                  if(pos + 1 < count) {                     
                     mask = types[pos];
                     
                     if((mask & SPECIAL) == SPECIAL) {
                        escape++;
                        length++;
                        pos++;
                     }
                  }
               }
               length++;
            }
            if(next == start && off > mark){
               return decoder.decode(mark, length, escape > 0); 
            }
         }
      }
      return null;
   }
   
   public String template() {
      int mark = off + 1;
      int pos = off + 1;
      
      if(pos < count) {
         char start = source[off];
         short mask = types[off];
         char next = start;
         
         if(next == '"') {
            int escape = 0;
            int length = 0;
            int variable = 0;
            
            while(pos < count) {
               next = source[pos++];
               
               if(next == start) {
                  if(variable > 0) {
                     off = pos;
                  }
                  break;
               }
               mask = types[pos -1];
               
               if((mask & DOLLAR) == DOLLAR){
                  variable++;
               } else if((mask & ESCAPE) == ESCAPE){
                  if(pos + 1 < count) {                     
                     mask = types[pos];
                     
                     if((mask & SPECIAL) == SPECIAL) {
                        escape++;
                        length++;
                        pos++;
                     }
                  }
               }
               length++;
            }
            if(next == start && off > mark && variable > 0){
               return decoder.decode(mark, length, escape > 0); 
            }
         }
      }
      return null;
   }
   
   public String type() {
      int mark = off;
      
      if(off < count) {
         short type = types[off];
         
         if((type & CAPITAL) == CAPITAL) {
            int length = 0;
            
            while(off < count) {
               type = types[off];
               
               if((type & (IDENTIFIER | DOLLAR)) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false); 
            }
         }
      }
      return null;             
   }   
   
   public String identifier() {
      int mark = off;
      
      if(off < count) {
         short type = types[off];
         
         if((type & LETTER) == LETTER) {
            int length = 0;
            
            while(off < count) {
               type = types[off];
               
               if((type & IDENTIFIER) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false); 
            }
         }
      }
      return null;             
   }
   
   public String qualifier() {  
      int mark = off;
      
      if(off < count) {
         short start = types[off];
         
         if((start & LETTER) == LETTER) {
            int length = 0;         
      
            while(off < count) {
               short next = types[off];
               
               if((next & (PERIOD | DOLLAR)) != 0) {
                  if(off + 1 < count) {
                     next = types[off + 1];
                     
                     if((next & LETTER) == 0) {
                        break; 
                     }
                  }                  
               } else if((next & IDENTIFIER) == 0) {
                  break;
               }
               length++;
               off++;
            }       
            if(length > 0) {
               return decoder.decode(mark, length, false);         
            }
         }
      }
      return null;
          
   }   
   
   public int reset(int mark) {
      int current = off;
      
      if(mark > count || mark < 0){
         throw new ParseException("Illegal reset for position " + mark);
      }
      off = mark; 
      return current;
   }  
   
   public int count() {
      return count;
   }
   
   public int mark() {
      return off;
   }    
}
