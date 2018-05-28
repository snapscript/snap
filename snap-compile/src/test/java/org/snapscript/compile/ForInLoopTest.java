package org.snapscript.compile;

import junit.framework.TestCase;

public class ForInLoopTest extends TestCase {

   private static final String SOURCE_1 =
   "for(let i in 0..9){\n"+
   "   assert i > -1;\n"+
   "}\n"+
   "for(var i in 0..9){\n"+
   "   assert i > -1;\n"+
   "}\n";
   
   public void testForInLoop() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
}
