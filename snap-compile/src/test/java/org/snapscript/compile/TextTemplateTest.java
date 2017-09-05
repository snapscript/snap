package org.snapscript.compile;

import junit.framework.TestCase;

public class TextTemplateTest extends TestCase {

   private static final String SOURCE_1=
   "var arr = new Integer[2];\n"+
   "println(\"arr[0]=${arr[0]}\");\n"+
   "println(\"arr[1]=${arr[1]}\");\n"+
   "arr[0]=2133;\n"+
   "assert \"${arr[0]}\" == '2133';\n";
   
   private static final String SOURCE_2 = 
   "function f(){\n"+
   "   var x = 1;\n"+
   "   println(\"x=${x}\");\n"+
   "   assert \"x=${x}\" == 'x=1';\n"+
   "}\n"+
   "f();\n";

   public void testTemplate() throws Exception {
      System.out.println(SOURCE_1);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public void testTemplateFromFunction() throws Exception {
      System.out.println(SOURCE_2);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }

   public static void main(String[] list) throws Exception {
      new TextTemplateTest().testTemplate();
   }
}