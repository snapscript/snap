package org.snapscript.core.bind;

import java.util.List;

import junit.framework.TestCase;

import org.snapscript.core.Context;
import org.snapscript.core.MockContext;
import org.snapscript.core.MockType;
import org.snapscript.core.function.index.FunctionPathFinder;
import org.snapscript.core.module.EmptyModule;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class SearchPathFinderTest extends TestCase {
   
   /*
    * class A {
    * }
    * class B extends A with X,Y,Z{
    * }
    * class C extends B{
    * }
    * class D extends C with I,J,K{
    * }
    * trait I{
    * }
    * 
    * [D, C, B, A, X, Y, Z, I, J, K]
    * MAYBE THIS IS BETTER: D,C,B,A,I,J,K,X,Y,Z,Runnable
    */
   public void testSearchPath() throws Exception {
      FunctionPathFinder finder = new FunctionPathFinder();
      Context context = new MockContext();
      Module module = new EmptyModule(context);
      Type a = new MockType(module, "A", null, null);
      Type b = new MockType(module, "B", null, null);
      Type c = new MockType(module, "C", null, null);
      Type d = new MockType(module, "D", null, null);
      Type x = new MockType(module, "X", null, null);
      Type y = new MockType(module, "Y", null, null);
      Type z = new MockType(module, "Z", null, null);
      Type i = new MockType(module, "I", null, null);
      Type j = new MockType(module, "J", null, null);
      Type k = new MockType(module, "K", null, null);
      
      List<Type> typesA = a.getTypes();
      List<Type> typesB = b.getTypes();
      List<Type> typesC = c.getTypes();
      List<Type> typesD = d.getTypes();
      
      typesB.add(a); // class B extends A with X,Y,Z
      typesB.add(x);
      typesB.add(y);
      typesB.add(z);
      
      typesC.add(b); // class C extends B
      
      typesD.add(c); // class D extends C with I,J,K
      typesD.add(i);
      typesD.add(j);
      typesD.add(k);
      
      List<Type> types = finder.findPath(d, "x");
      
      System.err.println(types);
      
      assertEquals(d, types.get(0));
      assertEquals(c, types.get(1));
      assertEquals(b, types.get(2));
      assertEquals(a, types.get(3));

      assertEquals(x, types.get(4));
      assertEquals(y, types.get(5));
      assertEquals(z, types.get(6));
      
      assertEquals(i, types.get(7));
      assertEquals(j, types.get(8));
      assertEquals(k, types.get(9));
      
   }

}
