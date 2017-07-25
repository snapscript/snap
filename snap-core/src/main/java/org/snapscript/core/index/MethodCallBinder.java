package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;

public class MethodCallBinder {
   
   private final MethodCall instance;
   private final MethodCall object;
   private final MethodCall base;
   
   public MethodCallBinder(Method method) {
      this.instance = new BaseCall(method);
      this.object = new ObjectCall(method);
      this.base = new SuperCall(method);
   }
   
   public MethodCall bind(Object target) {
      if(SuperInstance.class.isInstance(target)) {
         return base;
      }
      if(Instance.class.isInstance(target)) {
         return instance;
      }
      return object;
   }
}
