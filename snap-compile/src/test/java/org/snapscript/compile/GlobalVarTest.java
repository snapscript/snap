package org.snapscript.compile;

import junit.framework.TestCase;

public class GlobalVarTest extends TestCase {

   private static final String SOURCE_1=
   "const GLOBAL = 11;\n"+
   "function fun(n) {\n"+
   "   return GLOBAL + n;\n"+
   "}\n"+
   "var result = fun(30);\n"+
   "println(result);\n";
   
   private static final String SOURCE_2=
   "const GLOBAL = 11;\n"+
   "class X{\n"+
   "  ff(n) {\n"+
   "     return GLOBAL + n;\n"+
   "  }\n"+
   "}\n"+
   "var x = new X();\n"+
   "var result = x.ff(30);\n"+
   "println(result);\n";

   public void testGlobalVar() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
//   public void testGlobalVarInClass() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      Executable executable = compiler.compile(SOURCE_2);
//      executable.execute();
//   }

   public static void main(String[] list) throws Exception {
      new GlobalVarTest().testGlobalVar();
//      new GlobalVarTest().testGlobalVarInClass();
   }
}
