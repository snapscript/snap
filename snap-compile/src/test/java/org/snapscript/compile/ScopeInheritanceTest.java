package org.snapscript.compile;

import junit.framework.TestCase;

public class ScopeInheritanceTest extends ScriptTestCase {

   private static final String SOURCE =
    "for(i in 0..2){\n"+
    "   fun(i);\n"+
    "}\n"+
    "\n"+
    "func fun(i){\n"+
    "   println(i);\n"+
    "}\n";

   public void testInheritance() throws Exception {
      assertScriptCompiles(SOURCE);
   }

}
