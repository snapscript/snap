package org.snapscript.compile.staticanalysis;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;

public abstract class CompileTestCase extends TestCase {
   
   public static void assertCompileError(String source, String message) throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(source);
      boolean failure = false;
      try {
      compiler.compile(source).execute();
      }catch(Exception e){
         e.printStackTrace();
         failure=true;
         assertEquals(message, e.getMessage());
      }
      assertTrue("Should have failed: " + source, failure);
   }
   
   public static void assertCompileSuccess(String source) throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(source);
      compiler.compile(source).execute();
   }

}
