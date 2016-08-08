package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class TypeReference implements Evaluation {
   
   private Evaluation[] list;
   private Value type;
   
   public TypeReference(Evaluation... list) {
      this.list = list;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(type == null) {
         for(Evaluation part : list) {
            Value value = part.evaluate(scope, left);
            
            if(value == null) {
               throw new InternalStateException("Could not determine type");
            }
            left = value.getValue();
         }
         type = ValueType.getTransient(left);
      }
      return type;
   }

}
