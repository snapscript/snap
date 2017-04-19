
package org.snapscript.tree.variable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class VariableBinder {

   private final VariablePointerResolver resolver;
   
   public VariableBinder() {
      this.resolver = new VariablePointerResolver(this);
   }
   
   public Value bind(Scope scope, Object left, String name) {
      VariablePointer pointer = resolver.resolve(scope, left, name);
      Value value = pointer.get(scope, left);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}
