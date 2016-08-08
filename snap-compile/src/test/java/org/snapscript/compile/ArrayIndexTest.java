package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class ArrayIndexTest extends TestCase {

   private final static String SOURCE =
   "class Point{\n"+
   "   var x;\n"+
   "   var y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "}\n"+
   "var arr = new Point[10];\n"+
   "for(var i in 0..1000000){\n"+
   "   arr[2]=new Point(2,4);\n"+
   "}\n";
   
   public void testIndex() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      executable.execute();
      long end = System.currentTimeMillis();
      System.err.println(end-start);
   }
}
