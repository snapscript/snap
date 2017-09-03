package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class Expression extends Evaluation {
   
   private final Evaluation[] list;
   
   public Expression(Evaluation... list) {
      this.list = list;
   }

   @Override
   public Value compile(Scope scope, Object left) throws Exception {
      if(list.length <= 0) {
         throw new InternalStateException("Expression is empty");
      }
      for(int i = 0; i < list.length; i++){
         list[i].compile(scope, left);
      }
      return Value.getTransient(null);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(list.length <= 0) {
         throw new InternalStateException("Expression is empty");
      }
      Value value = list[0].evaluate(scope, left);
      
      for(int i = 1; i < list.length; i++){
         value = list[i].evaluate(scope, left);
      }
      return value;
   }
}