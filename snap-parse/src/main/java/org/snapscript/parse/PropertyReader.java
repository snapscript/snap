package org.snapscript.parse;

public abstract class PropertyReader<T> extends StatementReader<T> {
   
   protected PropertyReader(String file) {
      super(file);
   }

   @Override
   protected T create(char[] data, int off, int length, int line) {
      int seek = 0;

      while(seek < length) {
         char next = data[off + seek++];
         
         if(separator(next)) {
            String name = format(data, off, seek - 1);
            String value = format(data, off + seek, length - seek);
               
            return create(name, value);
         }
      }
      throw new StatementException("Error in '" + file + "' at line " + line);
   }
   
   protected String format(char[] data, int off, int length) {
      int finish = off + length;
      int start = off;

      while(start < finish) {
         char next = data[start];
         
         if(!space(next)) {
            break;
         }
         start++;
      }
      while(finish > start) {
         char next = data[finish-1];
         
         if(!space(next)) {
            break;
         }
         finish--;
      }
      return new String(data, start, finish -start);
   }
   
   protected boolean separator(char value) {
      return value == '=';
   }
   
   protected abstract T create(String name, String value);
}