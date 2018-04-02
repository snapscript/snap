package org.snapscript.core.function.match;

import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.scope.Value;
import org.snapscript.core.stack.ThreadStack;

public class ValueMatcher {
   
   private final ThreadStack stack;
   
   public ValueMatcher(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer match(Value value, Object... values) throws Exception { // match function variable
      Object object = value.getValue();
      
      if(Function.class.isInstance(object)) {
         Function function = (Function)object;
         Signature signature = function.getSignature();
         ArgumentConverter match = signature.getConverter();
         Score score = match.score(values);
         
         if(score.isValid()) {
            return new FunctionPointer(function, stack); 
         }
      }
      return null;
   }
}