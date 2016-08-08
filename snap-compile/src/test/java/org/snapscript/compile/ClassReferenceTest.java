package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.compile.Compiler;
import org.snapscript.core.EmptyModel;

public class ClassReferenceTest extends TestCase {

   private static final String SOURCE =
   "class X{}\n"+
   "System.err.println(X.class.getModule());\n";
   
   public void testClassReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
