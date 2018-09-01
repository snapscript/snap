package org.snapscript.compile.staticanalysis;

import org.snapscript.compile.ScriptTestCase;

public class GenericFunctionConstraintTest extends ScriptTestCase {

   private static final String SOURCE =
   "type Bag<T> = Map<T, T>;\n"+
   "func foo<T: Bag<String>>(a: T) {\n"+
   "   println(a);\n"+
   "}\n"+
   "foo<Map<String, String>>({:});\n";

   public void testGenericFunction() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
