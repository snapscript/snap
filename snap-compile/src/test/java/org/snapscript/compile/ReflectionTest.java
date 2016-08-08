package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;

import junit.framework.TestCase;

public class ReflectionTest extends TestCase {
   
   private static final String SOURCE =
   "module Mod{\n"+
   "   @Test\n"+
   "   func(){\n"+
   "   }\n"+
   "}\n"+
   "Mod.getFunctions().stream().forEach(func -> println(func.getAnnotations()));\n";
         
   
   public void testReflection() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
