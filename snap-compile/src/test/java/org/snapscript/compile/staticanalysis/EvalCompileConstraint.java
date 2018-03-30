package org.snapscript.compile.staticanalysis;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;

public class EvalCompileConstraint extends TestCase {

   private static final String SOURCE =
   "class Foo{\n"+
   "   const x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "}\n"+
   "eval('new Foo(1,2)').x;\n"; // eval should have no constraints
 
   public void testEvalHasNoConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }   
}
