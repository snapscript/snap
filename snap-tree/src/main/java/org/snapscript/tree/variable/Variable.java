
package org.snapscript.tree.variable;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class Variable implements Evaluation {
   
   private final NameReference reference;
   private final VariableBinder binder;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
      this.binder = new VariableBinder();
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      
      if(left == null) {
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value != null) { 
            return value;
         }
      }
      return binder.bind(scope, left, name);
   }  
}