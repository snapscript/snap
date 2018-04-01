package org.snapscript.compile.staticanalysis;

public class UnknownFieldCompileTest extends CompileTestCase {

   public static final String FAILURE_1 = 
   "class Color {\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   var c;\n"+
   "   func(){\n"+
   "      println(this.unknown);\n"+
   "   }\n"+
   "}\n"+
   "new Color().func();\n";

   public void testInvalidVariable() throws Exception {
      assertCompileError(FAILURE_1, "Could not resolve 'unknown' in scope in /default.snap at line 6");
   }
}
