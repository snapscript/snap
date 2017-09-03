package org.snapscript.compile;

import junit.framework.TestCase;

public class FunctionScopeTest extends TestCase {

   private static final String SOURCE =
   "function checkWithClosureCall() {\n"+
   "   var x = 0;\n"+
   "   var func = (a, b) -> isVariableInScope(a, b);\n"+
   "\n"+
   "   assert !func(1,2);\n"+ // not found?
   "}\n"+
   "\n"+
   "function isVariableInScope(a, b) {\n"+
   "   try {\n"+
   "      x++;\n"+
   "      println('OK');\n"+
   "      return true;\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "   }\n"+
   "   return false;\n"+
   "\n"+
   "}\n"+
   "checkWithClosureCall();\n";
   
   public void testFunctionScope() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
         
}
