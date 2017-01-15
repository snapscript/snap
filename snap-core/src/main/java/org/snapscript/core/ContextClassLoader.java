package org.snapscript.core;

public class ContextClassLoader extends ClassLoader {
   
   private final Class type;
   
   public ContextClassLoader(Class type) {
      this.type = type;
   }

   @Override
   public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      Thread thread = Thread.currentThread();
      ClassLoader context = thread.getContextClassLoader();
      ClassLoader caller = type.getClassLoader();
      
      try{
         return caller.loadClass(name);
      } catch(Exception e) {
         return context.loadClass(name);
      }
   }
}
