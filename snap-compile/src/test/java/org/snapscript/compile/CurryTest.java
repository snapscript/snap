package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.EmptyModel;

public class CurryTest extends TestCase {

   private static final String SOURCE =
   "module Curry {\n"+
   "   func(x){\n"+
   "      return (y) -> x+y;\n"+
   "   }\n"+
   "   func2(x){\n"+
   "      return [(y) -> x+y];\n"+
   "   }\n"+   
   "}\n"+
   "var f = Curry.func(1);"+
   "f(2);\n"+
   "assert Curry.func(1)(2) == 3;\n"+
   "assert Curry.func2(1)[0](2) == 3;\n";

   
   public void testClassReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
