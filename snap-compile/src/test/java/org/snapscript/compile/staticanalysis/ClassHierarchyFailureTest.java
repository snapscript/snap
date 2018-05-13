package org.snapscript.compile.staticanalysis;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.verify.VerifyException;

public class ClassHierarchyFailureTest extends TestCase {
   
   private static final String SOURCE_1 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a){}\n"+
   "}\n";       

   private static final String SOURCE_2 = 
   "class X{\n"+
   "   new(a, b){}\n"+
   "}\n"+
   "class Y extends X {\n"+
   "   new(a): super(a){}\n"+
   "}\n";
 
   private static final String SOURCE_3 =
   "class X{\n"+
   "   new(a: String, b: Date){}\n"+
   "}\n"+
   "class Y extends X{\n"+
   "   new(a): super('a', 11){}\n"+
   "}\n";
   
   
   public void testNoDefaultConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_1).execute();
      } catch(VerifyException e) {
         String message = e.getErrors().get(0).getCause().getMessage();
         e.printStackTrace();
         assertEquals("Function 'new(default.X)' not found for 'default.X'", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
   
   
   public void testNoMatchingSuperConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_2).execute();
      } catch(VerifyException e) {
         String message = e.getErrors().get(0).getCause().getMessage();
         e.printStackTrace();
         assertEquals("Function 'new(default.X, default.Any)' not found for 'default.X'", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
   
   
   public void testInvalidConstraintsOnSuperConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_3).execute();
      } catch(VerifyException e) {
         String message = e.getErrors().get(0).getCause().getMessage();
         e.printStackTrace();
         assertEquals("Function 'new(default.X, lang.String, lang.Integer)' not found for 'default.X'", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
}
