package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class CompoundTest extends TestCase {

   private static final String SOURCE=
   "var i = 1;\n"+
   "if(i>0){\n"+
   "  var x = i;\n"+
   "  x++;\n"+
   "}\n";

   public void testCompoundStatement() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();

   }
}