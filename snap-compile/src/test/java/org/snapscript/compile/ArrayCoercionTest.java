package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class ArrayCoercionTest extends TestCase {

   private static final String SOURCE_1 =
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
   
   private static final String SOURCE_2 =
   "class Score with Comparable {\n"+
   "   var score;\n"+
   "   new(score){\n"+
   "      this.score = score;\n"+
   "   }\n"+
   "   compareTo(other){\n"+
   "      return Double.compare(score, other.score);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return \"${score}\";\n"+
   "   }\n"+
   "}\n"+
   "var array1: Score[] = [new Score(1.1), new Score(1.0), new Score(2.0)];\n"+
   "//var list1: [] = array1;\n"+   
   "//var array2: [] = list1;\n"+   
   "//var list2: [] = array2;\n"+
   "\n"+
   "println(array1);\n"+   
   "//println(list1);\n"+   
   "//println(array2);\n"+   
   "//println(list2);\n";
    
   public void testArrayCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }  
   
   public void testScopeArrayCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }   
}
