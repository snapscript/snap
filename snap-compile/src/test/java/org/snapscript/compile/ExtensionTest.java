package org.snapscript.compile;

import junit.framework.TestCase;

public class ExtensionTest extends TestCase {

   private static final String SOURCE_1 = 
   "var list = new File(\".\").findFiles(\".*\");\n"+
   "println(list);\n";
   
   private static final String SOURCE_2 = 
   "println(new Date().getYear());\n";
   
   private static final String SOURCE_3 = 
   "let x = URL('http://www.google.com').get().response();\n"+
   "println(x);\n"+
   "println(x);"; 
   
   public void testFileExtension() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   public void testDateExtension() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }  
   public void testURLExtension() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }  
}
