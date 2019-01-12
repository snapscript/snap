package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;

public class AnyConstructor extends TypePart {
   
   private final TypeState state;
   
   public AnyConstructor() {
      this.state = new AnyState();
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      return state;
   }
}