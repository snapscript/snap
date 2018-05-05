package org.snapscript.core.type.index;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class ClassBoundsResolverTest extends TestCase {

   public static class GenericFunction<X extends Boolean, Y>{
      public <T extends String> T doIt(T value) {
         return value;
      }
      public X goForIt(String value) {
         return null;
      }
      public Y justDoIt() {
         return null;
      }
   }
   
   public void testBoundsResolver() throws Exception{
      ClassBoundResolver resolver = new ClassBoundResolver();
      Method doIt = GenericFunction.class.getDeclaredMethod("doIt", String.class);
      Method goForIt = GenericFunction.class.getDeclaredMethod("goForIt", String.class);
      Method justDoIt = GenericFunction.class.getDeclaredMethod("justDoIt");
      
      assertNotNull(doIt);
      assertEquals(resolver.resolveMethod(doIt).getName(), null); // its a method level generic, so ignore
      assertEquals(resolver.resolveMethod(doIt).getBound(), Object.class);
      
      assertNotNull(goForIt);
      assertEquals(resolver.resolveMethod(goForIt).getName(), "X");
      assertEquals(resolver.resolveMethod(goForIt).getBound(), Boolean.class);
      
      assertNotNull(justDoIt);
      assertEquals(resolver.resolveMethod(justDoIt).getName(), "Y");
      assertEquals(resolver.resolveMethod(justDoIt).getBound(), Object.class);
   }
}
