package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;

public class MethodCallBinder {
   
   private final MethodCall bridge;
   private final MethodCall object;
   private final MethodCall base;
   
   public MethodCallBinder(Method method) {
      this.bridge = new BridgeCall(method); 
      this.object = new ObjectCall(method); // this.
      this.base = new SuperCall(method); // super.
   }
   
   public MethodCall bind(Object target) {
      if(SuperInstance.class.isInstance(target)) {
         return base;
      }
      if(Instance.class.isInstance(target)) {
         return bridge;
      }
      return object;
   }
}
