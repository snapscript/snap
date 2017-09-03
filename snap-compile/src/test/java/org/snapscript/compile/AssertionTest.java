package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class AssertionTest extends TestCase {

   private static final String SOURCE=
   "var i = true;\n"+
   "assert true;\n"+
   "assert i;";

   public void testAssertions() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
