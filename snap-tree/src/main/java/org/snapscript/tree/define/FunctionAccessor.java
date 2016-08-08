package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;

public class FunctionAccessor implements Accessor<Scope> {

   private final Function function;
   
   public FunctionAccessor(Function function) {
      this.function = function;
   }
   
   @Override
   public Object getValue(Scope source) {
      try {
         Invocation invocation = function.getInvocation();
         Result result = invocation.invoke(source, source);
         
         return result.getValue();
      } catch(Exception e) {
         throw new InternalStateException("Illegal read access to " + function, e);
      }
   }

   @Override
   public void setValue(Scope source, Object value) {
      throw new InternalStateException("Illegal write access to " + function);
   }

}
