package org.snapscript.core.function.resolve;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.index.ReturnType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class FunctionCall implements Invocation {
   
   private final FunctionPointer pointer;
   
   public FunctionCall(FunctionPointer pointer) {
      this.pointer = pointer;
   }
   
   public boolean match(Scope scope, Object object, Object... list) throws Exception {
      return pointer.isCachable();
   }

   public Constraint check(Scope scope, Constraint left, Type... types) throws Exception {
      ReturnType type = pointer.getType(scope);

      if(type != null) {
         return type.check(left, types);
      }
      return NONE;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Invocation invocation = pointer.getInvocation();
      
      if(invocation != null) {
         return invocation.invoke(scope, object, list);
      }
      return null;
   } 
}
