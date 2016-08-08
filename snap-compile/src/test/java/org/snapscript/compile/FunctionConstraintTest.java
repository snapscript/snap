package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class FunctionConstraintTest extends TestCase {
   
   private static final String SOURCE_1=
   "invoke((a, b) -> a + b);\n"+
   "\n"+
   "function invoke(r: (a, b)) {\n"+
   "   println(r(22, 33));\n"+
   "}\n";
   
   private static final String SOURCE_2=
   "invoke((a: Integer, b) -> a + b);\n"+
   "\n"+
   "function invoke(r: (a: String, b)) {\n"+
   "   println('string='+r('22', 33));\n"+
   "}\n"+
   "function invoke(r: (a: Integer, b)) {\n"+
   "   println('integer='+r(22, 33));\n"+
   "}\n";

   private static final String SOURCE_3=
   "function x(f: (a, b)) {\n"+
   "   println(f(11,22));\n"+
   "}\n"+
   "function x(f: (a)) {\n"+
   "   println(f(113));\n"+
   "}\n"+
   "x((a,b)->a+b);\n";
   
   private static final String SOURCE_4=
   "function x(f: (a: String, b)) {\n"+
   "   println(f(11,22));\n"+
   "}\n"+
   "function x(f: (a)) {\n"+
   "   println(f(113));\n"+
   "}\n"+
   "function x(f: (a: String)) {\n"+
   "   return \"x:\" + f(113);\n"+
   "}\n"+   
   "function x(f: (a: Integer)) {\n"+
   "   throw 'illegal argument';\n"+
   "}\n"+     
   "var res = x((a:String)->\"res=${a}\");\n"+
   "println(res);\n";
   
   private static final String SOURCE_5=
   "function x(f: (a: String, b)) {\n"+
   "   println('x(f: (a: String, b))->' + f(11,22));\n"+
   "}\n"+
   "function x(f: (a, b)) {\n"+
   "   println('x(f: (a, b))->' + f(11,22));\n"+
   "}\n"+
   "x((a:String, b:String)->a+'='+b);\n";


   public void testAnyConstraints() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      System.err.println(SOURCE_1);
      executable.execute();
   }
   public void testTypeConstraints() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }
   
   public void testTypeConstraintsOnWidth() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
   
   public void testTypeConstraintsOnWidthAndType() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      System.err.println(SOURCE_4);
      executable.execute();
   }   
   
   public void testTypeBindingScore() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      System.err.println(SOURCE_5);
      executable.execute();
   }   
}
