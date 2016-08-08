package org.snapscript.compile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.MapModel;
import org.snapscript.core.Model;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class MathTest extends TestCase{
   
   private static final int ITERATIONS = 1000000;
   private static final String SOURCE = 
   "var x = 11;\n"+
   "var y = 2.0d;\n"+
   "var z = 55;\n"+
   "var r = 0;\n"+
   "\n"+
   "for(var i = 0; i < " + ITERATIONS +"; i++){\n"+
   "   //r = ((x / y) * z) + 6;\n"+
   "   r = calc(x,y,z);\n"+
   "}\n"+
   "function calc(x, y, z){\n"+
   "   return ((x / y) * z) + 6;\n"+
   "}\n";
         
   public void testMath() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Map map = new HashMap();
      Model model = new MapModel(map);
 
      map.put("x",11);
      map.put("y", 2.0d);
      map.put("z", 55);

      ExpressionEvaluator evaluator = context.getEvaluator();
      long start = System.nanoTime();
      for(int i = 0; i < ITERATIONS; i++) {
         double expect = ((11/2.0d)*55)+6;
         double result = evaluator.evaluate(model, "((x / y) * z) + 6");
         assertEquals(result, expect);
      }
      long finish = System.nanoTime();
      System.err.println("testMath() iterations="+ITERATIONS+" time="+TimeUnit.NANOSECONDS.toMillis(finish-start));
   }
   
   public void testMathInLoop() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.nanoTime();
      executable.execute();
      long finish = System.nanoTime();
      System.err.println("testMathInLoop() iterations="+ITERATIONS+" time="+TimeUnit.NANOSECONDS.toMillis(finish-start));
   }

   public static void main(String[] list) throws Exception {
      new MathTest().testMath();
      new MathTest().testMathInLoop();
   }
}