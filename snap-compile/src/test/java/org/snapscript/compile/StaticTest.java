package org.snapscript.compile;

import junit.framework.TestCase;

public class StaticTest extends TestCase{
   
   private static final String SOURCE = 
   "class Foo{\n"+
   "   static inc(){\n"+
   "      return 1;\n"+
   "   }\n"+
   "}\n"+
   "Foo.inc();\n";
         
   public void testStatic() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
