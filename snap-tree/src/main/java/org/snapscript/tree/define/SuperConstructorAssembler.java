package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypeState;
import org.snapscript.tree.ArgumentList;

public class SuperConstructorAssembler {

   private final ArgumentList arguments;

   public SuperConstructorAssembler(ArgumentList arguments){  
      this.arguments = arguments;
   } 

   public TypeState assemble(TypeBody body, Type type, Scope scope) throws Exception { 
      return new SuperState(arguments, type);
   }
}
