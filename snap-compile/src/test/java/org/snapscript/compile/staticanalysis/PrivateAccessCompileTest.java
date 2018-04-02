package org.snapscript.compile.staticanalysis;

public class PrivateAccessCompileTest extends CompileTestCase {

   private static final String FAILURE_1 =
   "class Typ {\n"+
   "   private func(){}\n"+
   "}"+
   "new Typ().func();\n";
   
   public void testModificationOfConstants() throws Exception {
      assertCompileError(FAILURE_1, "Function 'func' is private in /default.snap at line 3"); 
   }

}
