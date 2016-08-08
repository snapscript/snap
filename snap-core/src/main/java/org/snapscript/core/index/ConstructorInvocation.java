package org.snapscript.core.index;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;

public class ConstructorInvocation implements Invocation<Object> {

   private final Constructor constructor;
   
   public ConstructorInvocation(Constructor constructor) {
      this.constructor = constructor;
   }
   
   @Override
   public Result invoke(Scope scope, Object left, Object... list) throws Exception {
      if(constructor.isVarArgs()) {
         Class[] types = constructor.getParameterTypes();
         int require = types.length;
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;

         if(remaining >= 0) {
            Class type = types[require - 1];
            Class component = type.getComponentType();
            Object array = Array.newInstance(component, remaining);
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + constructor, e);
               }
            }
            Object[] copy = new Object[require];
            
            if(require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = array;
            list = copy;
         }
      }     
      Object value = constructor.newInstance(list);
      return ResultType.getNormal(value);
   }
}
