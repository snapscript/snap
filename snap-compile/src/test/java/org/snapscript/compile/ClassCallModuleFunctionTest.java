package org.snapscript.compile;

import junit.framework.TestCase;

public class ClassCallModuleFunctionTest extends TestCase {
   
   private static final String SOURCE = 
   "class Foo {\n"+
   "   call(){\n"+
   "      func();\n"+
   "   }\n"+
   "   func(x){\n"+
   "      println('error');\n"+
   "   }\n"+
   "}\n"+
   "function func(v...){\n"+
   "   println('x');\n"+
   "}\n"+
   "new Foo().call();\n";

   public void testModule() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
