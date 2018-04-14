package org.snapscript.core.function.index;

import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.variable.Value;

public class ValueIndexer {
   
   private final ThreadStack stack;
   
   public ValueIndexer(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer index(Value value, Object... values) throws Exception { // match function variable
      Object object = value.getValue();
      
      if(Function.class.isInstance(object)) {
         Function function = (Function)object;
         Signature signature = function.getSignature();
         ArgumentConverter match = signature.getConverter();
         Score score = match.score(values);
         
         if(score.isValid()) {
            return new TracePointer(function, stack); 
         }
      }
      return null;
   }
}