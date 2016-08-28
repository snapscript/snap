package org.snapscript.compile;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.core.Context;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class TypeLoaderTest extends TestCase {
   
   public static class ExampleObject {
      public void method(String s, int i, double d){
         System.out.println("method(String s="+s+", int i="+i+", double d="+d+")");
      }
      public void method(String s, Number n, double d){
         System.out.println("method(String s="+s+", Number n="+n+", double d="+d+")");
      }
      public void method(String s, long l, float d){
         System.out.println("method(String s="+s+", long l="+l+", float d="+d+")");
      }
      public void method(String s, String l, Integer... i){
         System.out.println("method(String s="+s+", String l="+l+", Integer... i)="+Arrays.asList(i)+"");
      }
   }
   
   public void testLoader() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      Type type1 = loader.resolveType("org.snapscript.compile", "TypeLoaderTest$ExampleObject", 2);
      Type type2 = loader.resolveType("lang", "Integer", 3);
      Type type3 = loader.resolveType("foo", "Blah", 3);
      System.err.println(type1);
      System.err.println(type2);
      System.err.println(type3);
   }

}
