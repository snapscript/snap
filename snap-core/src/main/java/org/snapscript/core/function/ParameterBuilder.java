package org.snapscript.core.function;

import org.snapscript.core.Type;

public class ParameterBuilder {
   
   private static final String[] PREFIX = {
   "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
   "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
   
   public Parameter create(Type type, String name) {
      return new Parameter(name, type);
   }

   public Parameter create(Type type, int index) {
      return create(type, index, false);
   }
   
   public Parameter create(Type type, int index, boolean variable) {
      String prefix = PREFIX[index % PREFIX.length];
      
      if(index > PREFIX.length) {
         prefix += index / PREFIX.length;
      }
      return new Parameter(prefix, type, variable);
   }
}
