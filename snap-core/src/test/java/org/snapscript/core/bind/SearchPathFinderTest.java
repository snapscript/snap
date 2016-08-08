package org.snapscript.core.bind;

import java.util.List;

import junit.framework.TestCase;

import org.snapscript.core.TestType;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionPathFinder;

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
      Type a = new TestType(null, "A", null, null);
      Type b = new TestType(null, "B", null, null);
      Type c = new TestType(null, "C", null, null);
      Type d = new TestType(null, "D", null, null);
      Type x = new TestType(null, "X", null, null);
      Type y = new TestType(null, "Y", null, null);
      Type z = new TestType(null, "Z", null, null);
      Type i = new TestType(null, "I", null, null);
      Type j = new TestType(null, "J", null, null);
      Type k = new TestType(null, "K", null, null);
      
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
