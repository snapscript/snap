
package org.snapscript.tree.define;

import java.lang.reflect.Constructor;

import org.snapscript.core.function.Invocation;

public class AnyInvocationGenerator {

   public AnyInvocationGenerator() {
      super();
   }

   public Invocation create(Class invoke) throws Exception {
      Constructor constructor = invoke.getDeclaredConstructor();
      
      if(!constructor.isAccessible()) {
         constructor.setAccessible(true);
      }
      return (Invocation)constructor.newInstance();
   }
}
