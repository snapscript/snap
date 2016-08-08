package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class InstanceOfTest extends TestCase {

   private static final String SOURCE=
   "assert {:}?=Map;\n"+
   "assert {}?=Set;\n"+
   "assert 1?=Integer;\n"+
   "assert 2f?=Float;\n"+
   "assert \"\"!?=null;";

   public void testRecursion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
