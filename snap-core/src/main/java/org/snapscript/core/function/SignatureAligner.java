package org.snapscript.core.function;

import java.lang.reflect.Array;
import java.util.List;

import org.snapscript.core.InternalStateException;

public class SignatureAligner {

   private final Signature signature;
   
   public SignatureAligner(Signature signature) {
      this.signature = signature;
   }
   
   public Object[] align(Object... list) throws Exception {
      if(signature.isVariable()) {
         List<Parameter> parameters = signature.getParameters();
         int length = parameters.size();
         int start = length - 1;
         int remaining = list.length - start;
         
         if(remaining > 0) {
            Object array = new Object[remaining];
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + signature, e);
               }
            }
            list[start] = array;
         }
         Object[] copy = new Object[length];
         
         for(int i = 0; i < length; i++) {
            copy[i] = list[i];
         }
         return copy;
      }
      return list;
   }
}
