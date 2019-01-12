package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class PrimitiveTypeResolver {
   
   private Type type;
   
   public PrimitiveTypeResolver() {
      super();
   }

   public Type resolve(Scope scope) {
      if(type == null) {
         Module parent = scope.getModule();
         Context context = parent.getContext();         
         TypeLoader loader = context.getLoader();
         
         type = loader.loadType(DEFAULT_PACKAGE, ANY_TYPE);
      }
      return type;
   }
}
