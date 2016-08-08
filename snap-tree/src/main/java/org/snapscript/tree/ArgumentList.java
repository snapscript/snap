package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class ArgumentList implements Evaluation{
   
   private final Argument[] list;
   
   public ArgumentList(Argument... list) {
      this.list = list;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      Object[] values = new Object[list.length];
      
      for(int i = 0; i < list.length; i++){
         Value reference = list[i].evaluate(scope, left);
         Object result = reference.getValue();
         
         values[i] = result;
      }
      return ValueType.getTransient(values);
   }
}