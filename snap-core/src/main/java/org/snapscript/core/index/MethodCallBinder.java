package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;
import org.snapscript.core.function.Invocation;

public class MethodCallBinder {
   
   private final MethodCall bridge;
   private final MethodCall object;
   private final MethodCall base;
   
   public MethodCallBinder(Invocation invocation, Method method) {
      this.object = new ObjectCall(invocation, method); // this.
      this.bridge = new BridgeCall(invocation); 
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