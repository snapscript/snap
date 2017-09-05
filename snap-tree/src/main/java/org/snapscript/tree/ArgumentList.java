package org.snapscript.tree;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class ArgumentList {
   
   private final Argument[] list;
   private final Object[] empty;
   
   public ArgumentList(Argument... list) {
      this.empty = new Object[]{};
      this.list = list;
   }
   
   public void compile(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].compile(scope);
      }
   }
   
   public Value create(Scope scope) throws Exception{
      return create(scope, empty);
   }
   
   public Value create(Scope scope, Object... prefix) throws Exception{
      Object[] values = new Object[list.length + prefix.length];
      
      for(int i = 0; i < list.length; i++){
         Value reference = list[i].evaluate(scope, null);
         Object result = reference.getValue();
         
         values[i + prefix.length] = result;
      }
      for(int i = 0; i < prefix.length; i++) {
         values[i] = prefix[i];
      }
      return Value.getTransient(values);
   }
}