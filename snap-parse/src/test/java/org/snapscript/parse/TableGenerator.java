package org.snapscript.parse;

public class TableGenerator {   
   
   short NONE = 0x0000;
   short LETTER = 0x0001;
   short DIGIT = 0x0002;
   short HEXIDECIMAL = 0x0004;
   short BINARY = 0x0008;
   short IDENTIFIER = 0x0010;   
   short QUOTE = 0x0020;
   short PERIOD = 0x0040;
   short SUFFIX = 0x0080;
   short MINUS = 0x0100;    
   short ESCAPE = 0x0200;
   short SPECIAL = 0x0400;
   short DOLLAR = 0x0800;
   short CAPITAL = 0x1000;
   
   public static void main(String[] list) {
      System.out.println("private static final short[] TYPES = {");
     
      for(int i = 0; i < 0xff; i++) {
         char next = (char)i;
         //System.out.print("/*"+i+"*/");
         if(digit(next)) {
            System.out.print("TextCategory.DIGIT | TextCategory.HEXIDECIMAL | TextCategory.IDENTIFIER"); 
            
            if(binary(next)) {
               System.out.println(" | TextCategory.BINARY,");
            } else {
               System.out.println(",");
            }
         } else if(letter(next)) {
            System.out.print("TextCategory.LETTER | TextCategory.IDENTIFIER"); 
            
            if(hexidecimal(next)) {
               System.out.print(" | TextCategory.HEXIDECIMAL");
            } 
            if(suffix(next)) {
               System.out.print(" | TextCategory.SUFFIX");
            }
            if(Character.isUpperCase(next)) {
               System.out.print(" | TextCategory.CAPITAL");
            }
            switch(next){
            case '\'': case '"':
            case '\\': case 'n':
            case 'b': case 'r':               
            case 't': case 'f':
            case 'u': case 'U':
               System.out.print(" | TextCategory.SPECIAL");
            }
            System.out.println(",");
         } else if(qualifier(next)){
            System.out.println("TextCategory.PERIOD,"); 
         } else if(quote(next)){
            System.out.print("TextCategory.QUOTE");
            switch(next){
            case '\'': case '"':
               System.out.println(" | TextCategory.SPECIAL,");
               break;
            default:
               System.err.println(",");
            }            
         } else if(next == '-') {
            System.out.println("TextCategory.MINUS,"); 
         } else {
            switch(next){
            case '\'': case '"':
            case '\\': case 'n':
            case 't': case 'f':
            case 'u': case 'U':               
               System.out.print("TextCategory.SPECIAL");
               if(next == '\\') {
                  System.out.println(" | TextCategory.ESCAPE,");
               } else {
                  System.out.println(",");
               }
               break;
            default:
               if(next=='$'){
                  System.out.println("TextCategory.DOLLAR,");
               }else if(next=='_'){
                  System.out.println("TextCategory.IDENTIFIER,");
               } else{
                  System.out.println("TextCategory.NONE,");
               }
            } 
         } 
      }
     
      System.out.println("};");      
   }
   private static boolean binary(char value){
      return value =='1'||value=='0';
   }
   private static boolean hexidecimal(char value) {
      if(value >= 'a' && value <= 'f') {
         return true;
      }
      if(value >= 'A' && value <= 'F') {
         return true;
      }
      return false;
   }
   
   
   
   private static boolean qualifier(char value) {
      return value == '.';
   } 
   
   private static boolean letter(char value) {
      if(value >= 'a' && value <= 'z') {
         return true;
      }
      if(value >= 'A' && value <= 'Z') {
         return true;
      }
      return false;
   }   
   
   private static boolean digit(char value) {
      return value >= '0' && value <= '9';
   }
   
   private static boolean quote(char value) {
      return value == '\'' || value == '"';
   } 
   
   private static boolean numeric(char value) {
      return digit(value) || suffix(value);
   }
   
   private static boolean suffix(char value) {
      switch(value) {
      case 'l': case 'L':
      case 'd': case 'D':
      case 'f': case 'F':
         return true;
      }
      return false;
   }
}
