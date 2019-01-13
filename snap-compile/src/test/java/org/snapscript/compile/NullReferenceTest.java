package org.snapscript.compile;

import org.snapscript.compile.staticanalysis.CompileTestCase;

public class NullReferenceTest extends CompileTestCase {
   
   private static final String SOURCE =
   "class Foo{\n"+
   "   static call<T: Number>(a: T): List<T> {\n"+
   "      return [a];\n"+
   "   }\n"+
   "}\n"+
   "let f = Foo.call<Double>(null).get(0).floatValue();\n"+
   "println(f);\n";
         
   
   public void testNullReference() throws Exception {
      assertCompileAndExecuteSuccess(SOURCE);
   }

}
