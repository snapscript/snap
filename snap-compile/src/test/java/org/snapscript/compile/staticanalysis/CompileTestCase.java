package org.snapscript.compile.staticanalysis;

import java.util.List;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.verify.VerifyError;
import org.snapscript.compile.verify.VerifyException;

public abstract class CompileTestCase extends TestCase {
   
   public static void assertCompileError(String source, String message) throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(source);
      boolean failure = false;
      try {
         compiler.compile(source).execute();
      }catch(VerifyException e){
         List<VerifyError> errors = e.getErrors();
         
         for(VerifyError error : errors) {
            error.getCause().printStackTrace();
            assertEquals(message, error.getDescription());
         }
         failure=true;
      }
      assertTrue("Should have failed: " + source, failure);
   }
   
   public static void assertCompileSuccess(String source) throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(source);
      compiler.compile(source).execute();
   }

}
