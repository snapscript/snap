package org.snapscript.tree;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class ArgumentList {
   
   private final Argument[] list;
   private final Object[] empty;
   private final Type[] none;
   
   public ArgumentList(Argument... list) {
      this.empty = new Object[]{};
      this.none = new Type[]{};
      this.list = list;
   }
   
   public void compile(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].compile(scope);
      }
   }
   
   public Object[] create(Scope scope) throws Exception{
      if(list.length > 0) {
         return create(scope, empty);
      }
      return empty;
   }
   
   public Object[] create(Scope scope, Object... prefix) throws Exception{
      Object[] values = new Object[list.length + prefix.length];
      
      for(int i = 0; i < list.length; i++){
         Value reference = list[i].evaluate(scope, null);
         Object result = reference.getValue();
         
         values[i + prefix.length] = result;
      }
      for(int i = 0; i < prefix.length; i++) {
         values[i] = prefix[i];
      }
      return values;
   }
   
   public Type[] validate(Scope scope) throws Exception{
      if(list.length > 0) {
         return validate(scope, none);
      }
      return none;
   }
   
   public Type[] validate(Scope scope, Type... prefix) throws Exception{
      Type[] values = new Type[list.length + prefix.length];
      
      for(int i = 0; i < list.length; i++){
         Constraint result = list[i].validate(scope, null);
         if(result == null){
            System.err.println();
         }
         Type type = result.getType(scope);
         
         values[i + prefix.length] = type;
      }
      for(int i = 0; i < prefix.length; i++) {
         values[i] = prefix[i];
      }
      return values;
   }
}