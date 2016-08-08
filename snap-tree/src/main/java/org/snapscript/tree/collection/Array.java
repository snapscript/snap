package org.snapscript.tree.collection;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class Array implements Evaluation {
   
   private final ArrayBuilder builder;
   private final Evaluation variable;
   
   public Array(Evaluation variable) {
      this.builder = new ArrayBuilder();
      this.variable = variable;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      Value value = variable.evaluate(scope, left);
      Object list = value.getValue();
      Class type = list.getClass();
      
      if(type.isArray()) {
         list = builder.convert(list);
      }     
      return ValueType.getTransient(list);
   }  
   

}