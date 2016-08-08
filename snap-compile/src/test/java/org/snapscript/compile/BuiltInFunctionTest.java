package org.snapscript.compile;

import junit.framework.TestCase;

public class BuiltInFunctionTest extends TestCase {
   
   private static final String SOURCE =
   "println(\"x\");\n"+
   "print(\"xx\");\n"+
   "println(time());\n"+
   "eval(\"blah('foo')\");\n"+
   "\n"+
   "function blah(x){\n"+
   "   println(\"blah(\"+x+\")\");\n"+
   "}\n";
         
   public void testBuiltInFunctions() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new BuiltInFunctionTest().testBuiltInFunctions();
   }
}
