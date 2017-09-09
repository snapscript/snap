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

public class FunctionSearcher {
   
   private final Signature signature;
   private final Function invalid;
   
   public FunctionSearcher() {
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
   }

   public Function search(List<Function> functions, String name, Type... types) throws Exception { 
      int size = functions.size();
      
      if(size > 0) {
         Function function = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            Function next = functions.get(i);
            String match = next.getName();
            
            if(match.equals(name)) {
               Signature signature = next.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(types);

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

   public Function search(List<Function> functions, String name, Object... values) throws Exception { 
      int size = functions.size();
      
      if(size > 0) {
         Function function = invalid;
         Score best = INVALID;
            
         for(int i = size - 1; i >= 0; i--) { 
            Function next = functions.get(i);
            String match = next.getName();
            
            if(match.equals(name)) {
               Signature signature = next.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(values);

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
