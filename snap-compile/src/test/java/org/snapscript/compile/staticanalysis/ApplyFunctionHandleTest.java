package org.snapscript.compile.staticanalysis;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;

import junit.framework.TestCase;

public class ApplyFunctionHandleTest extends TestCase {
   
   private static final String SOURCE_1 =
   "var map = {a: [Math.class]};\n"+
   "var func = map.a[0]::max;\n"+
   "var result = func(7,33);\n"+
   "\n"+
   "assert result == 33;\n";

   public void testHandle() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
}
