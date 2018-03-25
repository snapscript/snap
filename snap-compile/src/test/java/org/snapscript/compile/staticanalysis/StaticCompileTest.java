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
}
