package org.snapscript.compile;

import junit.framework.TestCase;

public class CoercionTest extends TestCase {

   private static final String SOURCE =
   "var x: String[] = ['a', 'b'];\n"+
   "var y: [] = new Object[1][1];\n";
         
   public void testCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      //executable.execute();
   } 
}
