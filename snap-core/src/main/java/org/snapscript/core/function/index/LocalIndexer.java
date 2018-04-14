package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class LocalIndexer {
   
   private final ThreadStack stack;
   
   public LocalIndexer(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer index(Scope scope, String name, Type... values) throws Exception { // match function variable
      State state = scope.getState();
      Value value = state.get(name);
      
      if(value != null) {
         Type type = value.getType(scope);
         
         if(type != null) {
            Category category = type.getCategory();
            List<Function> functions = type.getFunctions();
            
            if(category.isFunction() && !functions.isEmpty()) {
               Function function = functions.get(0);
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
         }
      }
      return null;
   }
   
   public FunctionPointer index(Scope scope, String name, Object... values) throws Exception { // match function variable
      State state = scope.getState();
      Value value = state.get(name);
      
      if(value != null) {
         Object object = value.getValue();
         
         if(object != null) {
            if(Function.class.isInstance(object)) {
               Function function = (Function)object;
               Signature signature = function.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);
               
               if(score.isValid()) {
                  return new TracePointer(function, stack);
               }
            }
         }
      }
      return null;
   }
}