package org.snapscript.compile.staticanalysis;

public class AliasDefinitionTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "type Foo = Map<String, String>;\n"+
   "let x: Foo = {:};\n"+
   "x.get('a').substring(1);\n";   
   
   private static final String SUCCESS_2 =
   "module Mod {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').substring(1);\n";
   
   private static final String SUCCESS_3 =
   "module Mod {\n"+
   "   type Foo = Map<String, Double>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').intValue();\n";
   
   private static final String SUCCESS_4 =
   "class Typ {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "new Typ().fun().get('a').substring(1);\n";
   
   private static final String FAILURE_1 =
   "type Foo = Map<String, String>;\n"+
   "let x: Foo = {:};\n"+
   "x.get('a').intValue();\n";
   
   private static final String FAILURE_2 =
   "module Mod {\n"+
   "   type Foo = Map<String, String>;\n"+
   "\n"+
   "   fun(): Foo {\n"+
   "      return {:};\n"+
   "   }\n"+
   "}\n"+
   "Mod.fun().get('a').intValue();\n";
   
   public void testGenericFunction() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileError(FAILURE_1, "Function 'intValue()' not found for 'lang.String' in /default.snap at line 3");
      assertCompileError(FAILURE_2, "Function 'intValue()' not found for 'lang.String' in /default.snap at line 8");
   }
}