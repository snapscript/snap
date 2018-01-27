package org.snapscript.core.shell;

import java.lang.reflect.Constructor;

public class ConstructorBuilder implements ShellBuilder {

   @Override
   public Object create(Class type) {
      try {
         Constructor constructor = type.getDeclaredConstructor();
         
         if(constructor != null) {
            constructor.setAccessible(true);
            return constructor.newInstance();
         }
      } catch(Exception e) {
         return null;
      }
      return null;
   }

}
