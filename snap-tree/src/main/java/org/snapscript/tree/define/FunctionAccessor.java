package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.index.TracePointer;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;

public class FunctionAccessor implements Accessor<Scope> {

   private final FunctionPointer pointer;
   private final Function function;
   
   public FunctionAccessor(Function function, ThreadStack stack) {
      this.pointer = new TracePointer(function, stack);
      this.function = function;
   }
   
   @Override
   public Object getValue(Scope source) {
      Invocation invocation = pointer.getInvocation();
      
      try {
         return invocation.invoke(source, source);
      } catch(Exception e) {
         throw new InternalStateException("Illegal read access to " + function, e);
      }
   }

   @Override
   public void setValue(Scope source, Object value) {
      throw new InternalStateException("Illegal write access to " + function);
   }

}