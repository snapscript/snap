package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.compile.Compiler;
import org.snapscript.core.EmptyModel;

public class ModuleTest extends TestCase {
   
   private static final String SOURCE_1=
   "@Blah\n"+
   "module M{\n"+
   "   class X{\n"+
   "      var i;\n"+
   "      new(i){\n"+
   "         this.i = i;\n"+
   "      }\n"+
   "      toString(){\n"+
   "         return \"\"+i;\n"+
   "      }\n"+
   "   }\n"+
   "   var x = new X(11);\n"+
   "   var y = x.class.getModule();\n"+
   "\n"+
   "   System.err.println(this);\n"+
   "   System.err.println(x);\n"+
   "}\n";

   private static final String SOURCE_2=
   "module Mod {\n"+
   "   createTyp(i){\n"+
   "      return new Typ(i);\n"+
   "   }\n"+
   "}\n"+
   "class Typ{\n"+
   "   var i;\n"+
   "   new(i){\n"+
   "      this.i = i;\n"+
   "   }\n"+
   "   toString() {\n"+
   "      return \"\"+i;\n"+
   "   }\n"+
   "}\n"+
   "System.err.println(Mod.createTyp(55));\n";


   public void testModuleInnerClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute(new EmptyModel());
   }
   
   public void testModuleOuterClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(new EmptyModel());
   }
}
