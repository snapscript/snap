package org.snapscript.parse;

public class GrammarReader extends StatementReader<GrammarDefinition> {
   
   public GrammarReader(String file) {
      super(file);
   }

   @Override
   protected GrammarDefinition create(char[] data, int off, int length, int line) {
      int mark = 0;
      int seek = 0;
      
      while(seek < length) {
         char next = data[off + seek];
         
         if(space(next)) {
            mark = off + seek;
            seek++;
            break;
         }
         seek++;
      }
      while(seek < length) {
         char next = data[off + seek++];
         
         if(separator(next)) {
            String name = new String(data, off, mark - off);
            String definition = new String(data, off + seek, length - seek);
               
            return new GrammarDefinition(name, definition);
         }
      }
      throw new StatementException("Error in '" + file + "' at line " + line);
   }

   @Override
   protected boolean terminal(char[] data, int off, int length) {
      return data[off] == ';';
   }
   
   protected boolean separator(char value) {
      return value == '=';
   }
}
