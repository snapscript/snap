package org.snapscript.compile.staticanalysis;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;

public class StaticCompileTest extends TestCase {

   private static final String SOURCE_1 = 
   "class Foo{\n"+
   "   static var x:Integer = init();\n"+
   "   static init(): Map {\n"+
   "      return null;\n"+
   "   }\n"+
   "}\n"+
   "println('x');";
   
   private static final String SOURCE_2 = 
   "class Foo{\n"+
   "   static func(){\n"+
   "     var f: Integer = init();\n"+
   "     return f;\n"+
   "   }\n"+      
   "   static init(): Map {\n"+      
   "      return null;\n"+
   "   }\n"+
   "}\n"+
   "println('x');";
   
   private static final String SOURCE_3=
   "class A {\n"+
   "   static create(name): B{\n"+
   "      return new B(name);\n"+
   "   }\n"+
   "   class B with Runnable{\n"+
   "      var name;\n"+
   "      new(name){\n"+
   "         this.name=name;\n"+
   "      }\n"+
   "      override run(){}\n"+
   "   }\n"+
   "}\n"+
   "var b = A.create('test');\n"+
   "assert b != null;\n"+
   "println(b);\n";
   
   private static final String SOURCE_4=
   "var b = A.create('test');\n"+
   "assert b != null;\n"+
   "println(b);\n"+
   "class A {\n"+
   "   static create(name): B{\n"+
   "      return new B(name);\n"+
   "   }\n"+
   "   class B with Runnable{\n"+
   "      var name;\n"+
   "      new(name){\n"+
   "         this.name=name;\n"+
   "      }\n"+
   "      override run(){}\n"+
   "   }\n"+
   "}\n";
   
   public void testStaticAssignmentCompileError() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_1);
         compiler.compile(SOURCE_1).execute();
      } catch(Exception e) {
         e.printStackTrace();
         String message = e.getMessage();
         assertEquals(message, "Variable 'x' does not match constraint 'lang.Integer' in /default.snap at line 2");
         failure = true;
      }
      assertTrue("Should be a compile failure", failure);
   }
   
   public void testStaticMethodCompileError() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_2);
         compiler.compile(SOURCE_2).execute();
      } catch(Exception e) {
         e.printStackTrace();
         String message = e.getMessage();
         assertEquals(message, "Variable 'f' does not match constraint 'lang.Integer' in /default.snap at line 3");
         failure = true;
      }
      assertTrue("Should be a compile failure", failure);

   }
   
   public void testStaticMethodCompile() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      compiler.compile(SOURCE_3).execute();
   }
   
   public void testStaticMethodCompileReferencedEarly() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_4);
      compiler.compile(SOURCE_4).execute();
   }
}
