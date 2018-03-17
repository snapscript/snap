package org.snapscript.compile;

import junit.framework.TestCase;

public class ConstraintTest extends TestCase {

   private static final String SOURCE_1 =
   "var x: String = 'hello';\n"+
   "var y: Integer = 1;\n"+
   "y++;\n"+
   "y.hashCode();\n"+
   "println(x);\n";

   
   private static final String SOURCE_2 =
   "System.err.println('x');";
   
   public void testCompoundStatement() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   
   }

   public void testStaticReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   
   }
}