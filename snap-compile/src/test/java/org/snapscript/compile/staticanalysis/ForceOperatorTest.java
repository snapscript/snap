package org.snapscript.compile.staticanalysis;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;

public class ForceOperatorTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo{\n"+
   "   func(){\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo{\n"+
   "   foo(){\n"+
   "   }\n"+
   "}\n"+   
   "var x: Foo = new Bar();\n"+
   "x.func();\n"+
   "x.foo();\n";

   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   func(){\n"+
   "   }\n"+
   "}\n"+
   "class Bar extends Foo{\n"+
   "   foo(){\n"+
   "   }\n"+
   "}\n"+
   "var x: Foo = new Bar();\n"+
   "x.func();\n"+
   "x!.foo();\n";
   
   public void testCompileAsNoFunctionMatches() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      boolean failure = true;
      
      try {
         compiler.compile(SOURCE_1).execute();
      } catch(Exception e) {
         String message = e.getMessage();
         e.printStackTrace();
         assertEquals("Method 'foo()' not found in scope in /default.snap at line 11", message);
         failure = true;
      }
      assertTrue("Should be a compile error", failure);
   }
   
   public void testSuccessWhenForced() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
   }   
}
