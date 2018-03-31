package org.snapscript.core.function.search;

import java.util.List;

import org.snapscript.core.Category;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class ScopeFunctionMatcher {
   
   private final ThreadStack stack;
   
   public ScopeFunctionMatcher(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer match(Scope scope, String name, Type... values) throws Exception { // match function variable
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
                  return new FunctionPointer(function, stack);
               }
            }
         }
      }
      return null;
   }
   
   public FunctionPointer match(Scope scope, String name, Object... values) throws Exception { // match function variable
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
                  return new FunctionPointer(function, stack);
               }
            }
         }
      }
      return null;
   }
}