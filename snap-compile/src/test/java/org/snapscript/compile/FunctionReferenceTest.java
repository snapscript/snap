package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.EmptyModel;

public class FunctionReferenceTest extends TestCase {

   private static final String SOURCE =
   "var s = \"hello\";\n"+
   "//var f = i -> s.substring(i);\n"+
   "var f = s::substring;\n"+
   "var r = f(1);\n"+
   "\n"+
   "println(f);\n";
   
   public void testFunctionReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
