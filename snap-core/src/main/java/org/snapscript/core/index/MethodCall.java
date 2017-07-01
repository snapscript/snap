
package org.snapscript.core.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.snapscript.core.Bug;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;
import org.snapscript.core.function.Invocation;

@Bug("this is a mess.. we need to handle this better")
public class MethodCall {

   private final Method method;
   
   public MethodCall(Method method) {
      this.method = method;
   }
   
   public Object call(Object object, Object[] arguments) throws Exception {
      String name = method.getName();
      
      try {
         if(object instanceof SuperInstance) {
            Instance inst =  ((Instance)object);
            Value value = inst.getState().get("real");
            Object real = value.getValue();
            Type type = inst.getType();
            Invocation proxy = inst.getModule().getContext().getProvider().create(type).createSuper(inst, real.getClass(), method);
            try {
               return proxy.invoke((Instance)object, real, arguments).getValue();
            }catch(Throwable e){
               throw new InvocationTargetException(e);
            }
         }
         if(object instanceof Instance) {
            object = ((Instance)object).getState().get("real").getValue();
         }
         try{
            return method.invoke(object, arguments);
         }catch(Throwable e){
            throw new RuntimeException(e);
         }
      }catch(InvocationTargetException cause) {
         Throwable target = cause.getTargetException();
         
         if(target != null) {
            throw new InternalStateException("Error occured invoking '" + name + "'", target);
         }
         throw cause;
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }
   }
}
