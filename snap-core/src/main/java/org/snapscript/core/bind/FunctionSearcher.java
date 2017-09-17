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
import org.snapscript.core.stack.ThreadStack;

public class FunctionSearcher {
   
   private final FunctionCall invalid;
   private final Signature signature;
   private final Function empty;
   
   public FunctionSearcher(ThreadStack stack) {
      this.signature = new EmptySignature();
      this.empty = new EmptyFunction(signature);
      this.invalid = new FunctionCall(empty, stack);
   }

   public FunctionCall search(List<FunctionCall> calls, String name, Type... types) throws Exception { 
      int size = calls.size();
      
      if(size > 0) {
         FunctionCall call = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            FunctionCall next = calls.get(i);
            Function function = next.getFunction();
            String match = function.getName();
            
            if(match.equals(name)) {
               Signature signature = function.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(types);

               if(score.compareTo(best) > 0) {
                  call = next;
                  best = score;
               }
            }
         }
         return call;
      }
      return invalid;
   }

   public FunctionCall search(List<FunctionCall> calls, String name, Object... values) throws Exception { 
      int size = calls.size();
      
      if(size > 0) {
         FunctionCall call = invalid;
         Score best = INVALID;
         
         for(int i = size - 1; i >= 0; i--) {
            FunctionCall next = calls.get(i);
            Function function = next.getFunction();
            String match = function.getName();
            
            if(match.equals(name)) {
               Signature signature = function.getSignature();
               ArgumentConverter converter = signature.getConverter();
               Score score = converter.score(values);

               if(score.compareTo(best) > 0) {
                  call = next;
                  best = score;
               }
            }
         }
         return call;
      }
      return invalid;
   }
}
