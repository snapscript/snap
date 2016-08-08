package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class ArrayCoercionTest extends TestCase {

   private static final String SOURCE =
   "var s : String[][] = [['a','b'],[]];\n"+
   "var i : Integer[][] = [['1','2', 3.0d, 11L],[2]];\n"+
   "\n"+
   "func(s);\n"+
   "func(i);\n"+
   "\n"+
   "function func(s: String[][]){\n"+
   "   for(var e in s){\n"+
   "      func(e);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function func(s: String[]){\n"+
   "   for(var e in s){\n"+
   "      println(\"e=${e}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function func(s: Float[][]){\n"+
   "   for(var e in s){\n"+
   "      func(e);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function func(s: Float[]){\n"+
   "   for(var e in s){\n"+
   "      println(\"e=${e}\");\n"+
   "   }\n"+
   "}\n";
    
   public void testArrayCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }   
}
