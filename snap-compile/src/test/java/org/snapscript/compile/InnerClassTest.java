package org.snapscript.compile;

import junit.framework.TestCase;

public class InnerClassTest extends TestCase {
   
   private static final String SOURCE_1=
   "class Outer {\n"+
   "\n"+
   "   class Inner {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      new(x,y){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "      }\n"+
   "      dump(){\n"+
   "         println(\"x=${x} y=${y}\");\n"+
   "      }\n"+
   "   }\n"+
   "   create(x,y){\n"+
   "      return new Inner(x,y);\n"+
   "   }\n"+
   "}\n"+
//   "var inner = new Outer.Inner(1,2);\n"+
//   "inner.dump();\n"+
   "var outer = new Outer();\n"+
   "var result = outer.create(33,44);\n"+
   "result.dump();\n";
   
   private static final String SOURCE_2=
   "class Parent {\n"+
   "\n"+
   "   enum Color {\n"+
   "      RED,\n"+
   "      GREEN,\n"+
   "      BLUE\n"+
   "   }\n"+
   "   dump() {\n"+
   "      println(Color.RED);\n"+
   "   }\n"+
   "}\n"+
   "var parent = new Parent();\n"+
   "parent.dump();\n";
   
   public void testInnerClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
//   public void testInnerEnum() throws Exception {
//      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
//      System.err.println(SOURCE_2);
//      Executable executable = compiler.compile(SOURCE_2);
//      executable.execute();
//   }
}
