package org.snapscript.tree;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class AccessChecker {
   
   public AccessChecker() {
      super();
   }

   public boolean isAccessible(Scope scope, Type owner) {
      Type caller = scope.getType();
      
      if(caller != null) {
         if(isSuper(scope, owner)) {
            return true;
         }
         if(isParallel(scope, owner)) {
            if(isInner(scope, owner)) {
               return true;
            }
            if(isOuter(scope, owner)) {
               return true;
            }
         }
      }
      return false;
   }
   
   private boolean isSuper(Scope scope, Type owner) {
      Type caller = scope.getType();
      
      if(caller != owner) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> types = extractor.getTypes(caller); // what is this scope
         
         return types.contains(owner);
      }
      return true;
   }
   
   private boolean isParallel(Scope scope, Type owner) { // same module
      Type caller = scope.getType();
      
      if(caller != null) {
         Module actual = caller.getModule();
         Module require = owner.getModule(); 
         
         return actual == require;
      }
      return false;
   }
   
   private boolean isOuter(Scope scope, Type owner) {
      Type caller = scope.getType();
      
      if(caller != null) {
         String inner = caller.getName();
         String outer = owner.getName();
         
         return inner.startsWith(outer);
      }
      return false;
   }
   
   private boolean isInner(Scope scope, Type owner) {
      Type caller = scope.getType();
      
      if(caller != null) {
         String inner = owner.getName();
         String outer = caller.getName();
         
         return inner.startsWith(outer);
      }
      return false;
   }
}
