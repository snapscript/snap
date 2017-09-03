package org.snapscript.tree.construct;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final NameReference reference;
   
   public MapKey(Evaluation key) {
      this.reference = new NameReference(key);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = reference.getName(scope);
      State state = scope.getState();
      Value value = state.getScope(name);
      
      if(value == null) {
         return ValueType.getTransient(name);
      }
      return value;
   }
}