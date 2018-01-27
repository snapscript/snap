package org.snapscript.core.shell;

import junit.framework.TestCase;

public class NativeBuilderTest extends TestCase {

   public static class Foo {
      
      private final String name;
      private final int value;
      
      public Foo(String name, int value) {
         this.name = name;
         this.value = value;
      }
   }
   
   public void testBuilder() throws Exception {
      NativeBuilder builder = new NativeBuilder();
      
      Foo foo1 = (Foo)builder.create(Foo.class);
      Foo foo2 = (Foo)builder.create(Foo.class);
      
      assertNotNull(foo1);
      assertNotNull(foo2);
   }
}
