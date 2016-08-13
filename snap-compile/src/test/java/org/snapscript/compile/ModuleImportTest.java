package org.snapscript.compile;

import org.snapscript.core.EmptyModel;

import junit.framework.TestCase;

public class ModuleImportTest extends TestCase {
   
   private static final String SOURCE =
   "module Mod {\n"+
   "   import util.concurrent.ConcurrentHashMap as M;\n"+
   "}\n"+
   "\n"+
   "var failure = false;\n"+
   "try{\n"+
   "   println(M.class);\n"+
   "}catch(e){\n"+
   "  e.printStackTrace();\n"+
   "  failure = true;\n;"+
   "}\n"+
   "assert failure;\n";
       
   public void testModuleImport() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }   
}
