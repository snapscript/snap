package org.snapscript.compile;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.sun.management.ThreadMXBean;

public class ClosureTest extends TestCase {

   private static final String SOURCE_1=
   "var x = (a,b)->{ return a+b;};\n"+
   "var r = x(1,33);\n"+
   "println(r);";
   
   private static final String SOURCE_2=
   "max((a,b)->{return a+b;});\n"+
   "\n"+
   "function max(func){\n"+
   "   var x = func('xx', 'bb');\n"+
   "   println(x);\n"+
   "}\n";
   
   private static final String SOURCE_3=
   "max((a,b)->a+b);\n"+
   "\n"+
   "function max(func){\n"+
   "   var x = func('xx', 'bb');\n"+
   "   println(x);\n"+
   "}\n";


   public void testClosure() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
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
   
   public void testClosureAsParameter() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
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
   
   public void testClosureEvaluationAsParameter() throws Exception {
      DecimalFormat format = new DecimalFormat("###,###,###,###,###");
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
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
      new ClosureTest().testClosure();
      new ClosureTest().testClosureAsParameter();
      new ClosureTest().testClosureEvaluationAsParameter();
   }
}