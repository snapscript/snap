package org.snapscript.core.variable.index;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class VariableIndexResolver {
   
   public VariableIndexResolver() {
      super();
   }
   
   public int resolve(Scope scope, Value left){
      if(left != null && left.getData() != null) {
         Type type = left.getData().getType();
         
         if(type != null) {
            return type.getOrder();
         }
      }
      return 0;
   }
}