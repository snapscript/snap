package org.snapscript.tree.condition;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.convert.CompatibilityChecker;

public class InstanceChecker {
   
   private final CompatibilityChecker checker;
   
   public InstanceChecker() {
      this.checker = new CompatibilityChecker();
   }
   
   public boolean instanceOf(Scope scope, Object left, Object right) {
      if(right != null) {
         try {
            Type type = (Type)right;

            if(left != null) {
               return checker.compatible(scope, left, type);
            }
         } catch(Exception e) {
            return false;
         }
      }
      return false;
   }

}
