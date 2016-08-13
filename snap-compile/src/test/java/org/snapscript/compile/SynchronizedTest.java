package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class SynchronizedTest extends TestCase {
   
   private static final String SOURCE =
   "class Blah{\n"+
   "   toString(){\n"+
   "      return 'blah';\n"+
   "   }\n"+
   "}\n"+
   "var blah = new Blah();\n"+
   "synchronized(blah){\n"+
   "   println(blah);\n"+
   "}\n";

   public void testSynchronized() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
