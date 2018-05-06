package org.snapscript.compile;

import junit.framework.TestCase;

public class ClassHierarchyExceptionTest extends TestCase {
   
   private static final String SOURCE = 
   "class Foo extends NoSuchClass {\n"+
   "   new(x): super(x){\n"+
   "   }\n"+
   "   override noSuchMethod(){\n"+
   "      return true;\n"+
   "   }\n"+
   "}\n"+
   "new Foo(11).noSuchMethod();\n";       
   
   public void testException() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      try{
         executable.execute();
      }catch(Exception e) {
         e.printStackTrace();
         assertEquals(e.getCause().getMessage(), "Could not resolve constraint");
      }
   }

}
