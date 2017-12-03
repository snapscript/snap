package org.snapscript.compile;


import junit.framework.TestCase;

public class NullCoalesceTest extends TestCase {
   
   private static final String SOURCE =
   "var x=null;\n"+
   "var y=11;\n"+
   "assert (null===x??y)==false;\n";


   public void testNullCoalesce() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
