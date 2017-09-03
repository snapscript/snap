package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.EmptyModel;

public class FunctionTest extends TestCase {

   private static final String SOURCE =
   "var global =1;\n"+
   "\n"+
   "function a(){\n"+
   "   var x=0;\n"+
   "   var y =0;\n"+
   "   var z=0;\n"+
   "   var v=x+y+z+1;\n"+
   "}\n"+
   "\n"+
   "function b(){\n"+
   "   var aa = 0;\n"+
   "   if(aa==0){\n"+
   "      var y=0;\n"+
   "      y++;\n"+
   "   }\n"+
   "}\n"+
   "a();\n"+
   "b();\n";
         
   public void testFunction() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
