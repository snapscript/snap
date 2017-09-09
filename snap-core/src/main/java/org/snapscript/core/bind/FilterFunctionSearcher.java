package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class FilterFunctionSearcher implements FunctionSearcher {
   
   private final Signature signature;
   private final Function invalid;
   private final boolean accept;
   private final int filter;
   
   public FilterFunctionSearcher(int filter) {
      this(filter, true);
   }
   
   public FilterFunctionSearcher(int filter, boolean accept) {
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
      this.filter = filter;
      this.accept = accept;
   }

   @Override
   public Function search(List<Function> functions, Type... types) throws Exception { 
      int size = functions.size();
      
      if(size > 0) {
         Function function = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            Function next = functions.get(i);
            int modifiers = next.getModifiers();
            
            if((modifiers & filter) == filter == accept) { // if filter matches then accept
               Signature signature = next.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(types);

               if(score.compareTo(best) > 0) {
                  function = next;
                  best = score;
               }
            }
         }
         return function;
      }
      return invalid;
   }

   @Override
   public Function search(List<Function> functions, Object... values) throws Exception { 
      int size = functions.size();
      
      if(size > 0) {
         Function function = invalid;
         Score best = INVALID;
            
         for(int i = size - 1; i >= 0; i--) { 
            Function next = functions.get(i);
            int modifiers = next.getModifiers();
            
            if((modifiers & filter) == filter == accept) { // if filter matches then accept
               Signature signature = next.getSignature();
               ArgumentConverter match = signature.getConverter();
               Score score = match.score(values);

               if(score.compareTo(best) > 0) {
                  function = next;
                  best = score;
               }
            }
         }
         return function;
      }      
      return invalid;
   }
}
