
package org.snapscript.tree.variable;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameExtractor;

public class Variable implements Evaluation {
   
   private final NameExtractor extractor;
   private final VariableBinder binder;
   
   public Variable(Evaluation identifier) {
      this.extractor = new NameExtractor(identifier);
      this.binder = new VariableBinder();
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = extractor.extract(scope);
      
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