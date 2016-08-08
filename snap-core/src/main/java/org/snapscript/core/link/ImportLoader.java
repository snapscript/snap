package org.snapscript.core.link;

import java.lang.Package;

public class ImportLoader {
   
   public static Package getPackage(String name) {
      try {
         return Package.getPackage(name);
      }catch(Exception e) {
         return null;
      }
   }
   
   public static Class getClass(String name) {
      try {
         return Class.forName(name);
      } catch(Exception e) {
         return getContextClass(name);
      }
   }
   
   private static Class getContextClass(String name) {
      try {
         Thread thread = Thread.currentThread();
         ClassLoader loader = thread.getContextClassLoader();
         
         return loader.loadClass(name);
      }catch(Exception e) {
         return null;
      }
   }
}
