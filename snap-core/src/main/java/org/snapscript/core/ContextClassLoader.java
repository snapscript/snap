package org.snapscript.core;

public class ContextClassLoader extends ClassLoader {
   
   private final Class type;
   
   public ContextClassLoader(Class type) {
      this.type = type;
   }

   @Override
   public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      ClassLoader loader = type.getClassLoader();
      
      try{
         return loader.loadClass(name);
      } catch(Exception e) {
         Thread thread = Thread.currentThread();
         ClassLoader context = thread.getContextClassLoader();
         
         return context.loadClass(name);
      }
   }
}