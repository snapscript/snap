package org.snapscript.compile;

import junit.framework.TestCase;

public class DuplicateVariableTest extends TestCase {

   private static final String SOURCE =
   "var x = 10;\n"+
   "if(x>2){\n"+
   "   var x = 11;\n"+
   "   x++;\n"+
   "}\n";
   
   public void testDuplicate() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      boolean failure = false;
      try {
         executable.execute();
      }catch(Throwable e){
         e.printStackTrace();
         String message = e.getMessage();
         assertTrue(message.contains("at line 3"));
         failure = true;
      }
      assertTrue("Duplicate 'x' should cause failure", failure);
   }

}
