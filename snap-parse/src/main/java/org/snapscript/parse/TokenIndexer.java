package org.snapscript.parse;

import static org.snapscript.parse.TokenType.BINARY;
import static org.snapscript.parse.TokenType.DECIMAL;
import static org.snapscript.parse.TokenType.HEXIDECIMAL;
import static org.snapscript.parse.TokenType.IDENTIFIER;
import static org.snapscript.parse.TokenType.LITERAL;
import static org.snapscript.parse.TokenType.QUALIFIER;
import static org.snapscript.parse.TokenType.SPACE;
import static org.snapscript.parse.TokenType.TEMPLATE;
import static org.snapscript.parse.TokenType.TEXT;
import static org.snapscript.parse.TokenType.TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenIndexer {

   private final LengthComparator comparator;
   private final LineExtractor extractor;
   private final GrammarIndexer indexer;
   private final TextReader reader;
   private final List<String> values;
   private final short[] lines;

   public TokenIndexer(GrammarIndexer indexer, String resource, char[] original, char[] source, short[] lines, short[] types) {
      this.extractor = new LineExtractor(resource, original);
      this.reader = new TextReader(source, types);
      this.comparator = new LengthComparator();
      this.values = new ArrayList<String>();
      this.indexer = indexer;
      this.lines = lines;
   }

   public short[] index(List<Token> tokens) {
      if(values.isEmpty()) {
         List<String> literals = indexer.list();
        
         for(String literal : literals) {
            values.add(literal);
         }
         Collections.sort(values, comparator);
      }
      return scan(tokens);
   }
   
   private short[] scan(List<Token> tokens) {
      int count = reader.count();

      while (true) {
         int mark = reader.mark();
         
         if(mark >= count) {
            return create(tokens);
         }
         int line = lines[mark];
         Token token = literal(line);
         
         if (token == null) {
            token = space(line);
         }
         if (token == null) {
            token = template(line);
         }
         if (token == null) {
            token = text(line);
         }
         if(token == null) {
            token = type(line);
         }
         if(token == null) {
            token = identifier(line);
         }
         if(token == null) {
            token = binary(line);
         }
         if(token == null) {
            token = hexidecimal(line);
         }
         if(token == null) {
            token = decimal(line);
         }
         if(token == null) {
            throw new ParseException("Could not parse token at line " + lines[mark]);
         } 
         tokens.add(token);
      }
   }
   
   private short[] create(List<Token> tokens) {
      int length = tokens.size();
      
      if(length > 0) {
         short[] masks = new short[length];
         
         for(int i = 0; i < length; i++) {
            Token token = tokens.get(i);
            
            if(token != null) {
               masks[i] = token.getType();
            }
         }
         return masks;
      }
      return new short[]{};
   }
   
   private Token type(int number) {
      Line line = extractor.extract(number);
      String token = reader.type();

      if (token != null) {
         return new StringToken(token, line, TYPE.mask | QUALIFIER.mask | IDENTIFIER.mask);
      }
      return null;
   }

   private Token identifier(int number) {
      Line line = extractor.extract(number);
      String token = reader.identifier();
      
      if (token != null) {
         return new StringToken(token, line, IDENTIFIER.mask | QUALIFIER.mask);
      }
      return null;
   }

   private Token decimal(int number) {
      Line line = extractor.extract(number);
      Number token = reader.decimal();

      if (token != null) {
         return new NumberToken(token, line, DECIMAL.mask);
      }
      return null;
   }
   
   private Token binary(int number) {
      Line line = extractor.extract(number);
      Number token = reader.binary();
      
      if (token != null) {
         return new NumberToken(token, line, BINARY.mask | DECIMAL.mask);
      }
      return null;
   }

   private Token hexidecimal(int number) {
      Line line = extractor.extract(number);
      Number token = reader.hexidecimal();
      
      if (token != null) {
         return new NumberToken(token, line, HEXIDECIMAL.mask | DECIMAL.mask);
      }
      return null;
   }

   private Token template(int number) {
      Line line = extractor.extract(number);
      String token = reader.template();
      
      if (token != null) {
         return new StringToken(token, line, TEMPLATE.mask);
      }
      return null;
   }
   
   private Token text(int number) {
      Line line = extractor.extract(number);
      String token = reader.text();
      
      if (token != null) {
         return new StringToken(token, line, TEXT.mask);
      }
      return null;
   }
   
   private Token space(int number) {
      Line line = extractor.extract(number);
      Character token = reader.space();
      
      if (token != null) {
         return new CharacterToken(token, line, SPACE.mask);
      }
      return null;
   }
   
   private Token literal(int number) {
      Line line = extractor.extract(number);
      
      for (String literal : values) {
         int mark = reader.mark();
         String token = reader.literal(literal);

         if (token != null) {
            int length = token.length();
            Character last = token.charAt(length - 1);
            Character peek = reader.peek();
            
            if (identifier(last) && identifier(peek)) {
               reader.reset(mark);
            } else {
               if(identifier(last) && special(peek)) {
                  return new StringToken(token, line, LITERAL.mask | IDENTIFIER.mask | QUALIFIER.mask);
               }
               return new StringToken(token, line, LITERAL.mask);
            }
         }
      }
      return null;
   }
   
   private boolean identifier(Character value) {
      if (value != null) {
         if (Character.isLetter(value)) {
            return true;
         }
         if (Character.isDigit(value)) {
            return true;
         }
      }
      return false;
   }
   
   private boolean special(Character value) {
      if(value != null) {
         switch(value) {
         case ')': case '(':
         case '.': case '?':
         case ',': 
            return true;
         }
      }
      return false;
   }
}