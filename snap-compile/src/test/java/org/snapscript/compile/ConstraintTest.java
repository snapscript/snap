package org.snapscript.compile;

import junit.framework.TestCase;

public class ConstraintTest extends TestCase {

   private static final String SOURCE =
   "var x: String = 'hello';\n"+
   "var y: Integer = 1;\n"+
   "y++;\n"+
   "y.hashCode();\n"+
   "println(x);\n";

   public void testCompoundStatement() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   
   }
}