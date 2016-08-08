package org.snapscript.compile;

import junit.framework.TestCase;

public class TextTemplateTest extends TestCase {

   private static final String SOURCE=
   "var arr = new Integer[2];\n"+
   "println(\"arr[1]=\\${arr[1]}\");\n"+
   "println(\"arr[1]=${arr[1]}\");\n"+
   "println(\"arr[1]=\\\\${arr[1]}\");\n"+
   "println(\"arr[1]=\\\\\\${arr[1]}\");\n";

   public void testTemplate() throws Exception {
      System.out.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }

   public static void main(String[] list) throws Exception {
      new TextTemplateTest().testTemplate();
   }
}