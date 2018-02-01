package org.snapscript.tree.variable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class VariableIndexResolver {
   
   public VariableIndexResolver() {
      super();
   }
   
   public int resolve(Scope scope, Object left){
      if(left != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(left);
         
         if(type != null) {
            return type.getOrder();
         }
      }
      return 0;
   }
}