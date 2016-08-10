package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class InstanceOfTest extends TestCase {

   private static final String SOURCE=
   "class X{}\n"+
   "class Y extends X{}\n"+
   "var y = new Y();\n"+
   "\n"+
   "assert y instanceof X;\n"+
   "assert {:}instanceof Map;\n"+
   "assert {}instanceof Set;\n"+
   "assert 1 instanceof Integer;\n"+
   "assert 2f instanceof Float;\n"+
   "assert \"\"!instanceof null;";

   public void testRecursion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      System.err.println(SOURCE);
      executable.execute();
   }
}
