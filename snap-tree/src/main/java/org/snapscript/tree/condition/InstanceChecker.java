package org.snapscript.tree.condition;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class InstanceChecker {
   
   public InstanceChecker() {
     super();
   }
   
   public boolean instanceOf(Scope scope, Object left, Object right) {
      if(right != null) {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Type require = (Type)right;

            if(left != null) {
               Type actual = extractor.getType(left);
               Set<Type> types = extractor.getTypes(actual);
               
               return types.contains(require);
            }
         } catch(Exception e) {
            return false;
         }
      }
      return false;
   }

}
