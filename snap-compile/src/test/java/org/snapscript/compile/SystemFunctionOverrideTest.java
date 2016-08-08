package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class SystemFunctionOverrideTest extends TestCase {

   private static final String SOURCE=
//   "module Mod{}\n"+
//   "println(Mod.this.getFunctions());"+
   "function print(n) {\n"+
   "   println(\"XXXXXX ${n}\");\n"+
   "}\n"+
   "print(12);\n";
   
   public void testFunctions() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
