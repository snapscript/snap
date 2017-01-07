package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class TypeValue implements Evaluation {
   
   private final Type type;
   
   public TypeValue(Type type) {
      this.type = type;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return ValueType.getTransient(type);
   }
}