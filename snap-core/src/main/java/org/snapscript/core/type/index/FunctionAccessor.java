package org.snapscript.core.type.index;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.index.TracePointer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;

public class FunctionAccessor implements Accessor {

   private final FunctionPointer pointer;
   private final Function function;
   private final Module module;
   
   public FunctionAccessor(Function function, ThreadStack stack, Module module) {
      this.pointer = new TracePointer(function, stack);
      this.function = function;
      this.module = module;
   }
   
   @Override
   public Object getValue(Object source) {
      Invocation invocation = pointer.getInvocation();
      Scope scope = module.getScope();
      
      try {
         if(Scope.class.isInstance(source)) {
            return invocation.invoke((Scope)source, source);
         }
         return invocation.invoke(scope, source);
      } catch(Exception e) {
         throw new InternalStateException("Error occured invoking '" + function + "'", e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      throw new InternalStateException("Illegal modification of '" + function + "'");
   }

}