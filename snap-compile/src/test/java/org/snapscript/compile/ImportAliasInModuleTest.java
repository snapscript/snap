package org.snapscript.compile;

import junit.framework.TestCase;

public class ImportAliasInModuleTest extends TestCase {

   private static final String SOURCE =
   "import util.concurrent.CountDownLatch as L;\n"+
   "import util.concurrent.CountDownLatch;\n"+         
   "module Mod {\n"+
   "   import util.concurrent.ConcurrentHashMap as M;\n"+
   "   import util.concurrent.CopyOnWriteArrayList as L;\n"+
   "   getM(){\n"+
   "      println(M.class);\n"+
   "      return new M();\n"+
   "   }\n"+
   "   getL(){\n"+
   "      println(L.class);\n"+
   "      return new L();\n"+
   "   }\n"+
   "}\n"+
   "var l = Mod.getL();\n"+
   "var m = Mod.getM();\n"+
   "println(l.class);\n"+
   "println(m.class);\n";

   public void testAssertions() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }   
   
}
