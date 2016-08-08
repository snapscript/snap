package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class ArrayConstraintTest extends TestCase {

   private static final String SOURCE_1=
   "var x1: Integer[] = new Integer[1];\n"+
   "var x2: Integer[][] = new Integer[2][2];\n"+
   "\n"+
   "dump(x2);\n"+
   "dump(x1);\n"+
   "\n"+
   "function dump(x: Integer[][]){\n"+
   "  println('Integer[][]');\n"+
   "}\n"+
   "\n"+
   "function dump(x: Integer[]){\n"+
   "  println('Integer[]');\n"+
   "}";
   
   private static final String SOURCE_2=
   "var x1: Integer[] = new Integer[1];\n"+
   "var x2: Integer[][] = new Double[2][2];\n";
   
   private static final String SOURCE_3=
   "var x1: [] = [1, 2, 3];\n"+
   "println(x1);\n";
   
   private static final String SOURCE_4=
   "var x1: {} = {1, 2, 3, 3};\n"+
   "println(x1);\n";
   
   private static final String SOURCE_5=
   "var x1: {:} = {a:1, b:2, c:3, d:3};\n"+
   "println(x1);\n";

   public void testArrayConstraint() throws Exception {
      System.err.println(SOURCE_1);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testArrayConstraintFailure() throws Exception {
      System.err.println(SOURCE_2);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
   
   public void testListConstraint() throws Exception {
      System.err.println(SOURCE_3);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      executable.execute();
   }
   
   public void testSetConstraint() throws Exception {
      System.err.println(SOURCE_4);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_4);
      executable.execute();
   }
   
   public void testMapConstraint() throws Exception {
      System.err.println(SOURCE_5);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_5);
      executable.execute();
   }
}
