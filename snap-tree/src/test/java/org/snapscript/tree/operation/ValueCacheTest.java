package org.snapscript.tree.operation;

import junit.framework.TestCase;

import org.snapscript.core.scope.ModelScope;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class ValueCacheTest extends TestCase {
   
   private static final int ITERATIONS = 1000000;
   
   public void testValueCache() throws Exception {
      final Scope scope = new ModelScope(null, null);
      
      runIt("create: ", new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++) {
               for(int j = 0; j < 100; j++) {
                  Value.getTransient(j, null);
               }
            }
         }
      });
      
      runIt("cache: ", new Runnable() {
         @Override
         public void run(){
            for(int i = 0; i < ITERATIONS; i++) {
               for(int j = 0; j < 100; j++) {
                  ValueCache.getInteger(scope, j);
               }
            }
         }
      });
   }
   
   public static void runIt(String message, Runnable task) {
      long start = System.currentTimeMillis();
      task.run();
      long finish = System.currentTimeMillis();
      System.err.println(message +": "+(finish-start));
   }

}
