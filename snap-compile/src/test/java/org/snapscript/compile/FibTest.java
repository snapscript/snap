package org.snapscript.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class FibTest extends TestCase {

   private static final String SOURCE=
   "function fib(n) {\n"+
   "   if (n<2) {\n"+
   "      return 1;\n"+
   "   }\n"+
   "   return fib(n-1) + fib(n-2);\n"+
   "}\n"+
   "var result = fib(30);\n"+
   "println(result);\n";

   //time=1498 memory=1,933,564,016
   //time=1514 memory=1,933,196,408
   //time=1498 memory=1,933,298,408
   //time=1497 memory=1,933,550,080
   //time=1502 memory=1,933,420,704
   //time=1517 memory=1,933,574,016
   
   //time=1619 memory=1,769,799,896
   //time=1572 memory=1,769,659,520
   
   //time=1263 memory=1,586,663,120
   //time=1279 memory=1,586,655,976
   //time=1299 memory=1,586,667,584
   //time=1310 memory=1,586,589,152
   public void testRecursion() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      timeExecution(executable);
      timeExecution(executable);
      timeExecution(executable);
   }
   
   private void timeExecution(Executable executable) throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
      Thread thread = Thread.currentThread();
      long id = thread.getId();
      System.gc();
      System.gc();
      Thread.sleep(100);
      long before = bean.getThreadAllocatedBytes(id);
      long start = System.currentTimeMillis();
      executable.execute();
      long finish = System.currentTimeMillis();
      long after = bean.getThreadAllocatedBytes(id);
      System.out.println();
      System.out.println("time=" + (finish - start) + " memory=" + format.format(after - before));
   }

   public static void main(String[] list) throws Exception {
      new FibTest().testRecursion();
   }
}
