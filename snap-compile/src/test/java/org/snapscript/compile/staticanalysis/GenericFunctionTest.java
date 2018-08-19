package org.snapscript.compile.staticanalysis;

public class GenericFunctionTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "func fun<T: Integer>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).intValue();\n";
   
   private static final String SUCCESS_2 =
   "class Foo<K, V>{\n"+
   "   fun<T: Integer>(a: K, b: V): List<T> {\n"+
   "      return [1,2,3];\n"+
   "   }\n"+
   "}\n"+
   "new Foo<String, Double>().fun('a', 1.1).get(0).intValue();\n";
   
   private static final String SUCCESS_3 =
   "func fun<T>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).intValue();\n";
   
   private static final String SUCCESS_4 =
   "func fun<T>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun(1.0).intValue();\n"; // no qualification at all
   
   private static final String FAILURE_1 =
   "func fun<T: Integer>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun<Double>(1.0).substring(1);\n";
   
   private static final String FAILURE_2 =
   "class Foo<K, V>{\n"+
   "   fun<T: Integer>(a: K, b: V): List<T> {\n"+
   "      return [1,2,3];\n"+
   "   }\n"+
   "}\n"+
   "new Foo<String, Double>().fun('a', 1.1).get(0).substring(1);\n";
   
   private static final String FAILURE_3 =
   "func fun<T: Integer>(a: T): T {\n"+
   "  return a;\n"+
   "}\n"+
   "fun(1.0).substring(1);\n"; // no qualifier so default
   
   public void testGenericFunction() throws Exception {
//      assertCompileAndExecuteSuccess(SUCCESS_1);
//      assertCompileAndExecuteSuccess(SUCCESS_2);
//      assertCompileAndExecuteSuccess(SUCCESS_3);
//      assertCompileAndExecuteSuccess(SUCCESS_4);
//      assertCompileAndExecuteSuccess(SUCCESS_4);
//      assertCompileError(FAILURE_1, "Function 'substring(lang.Integer)' not found for 'lang.Double' in /default.snap at line 4");
      assertCompileError(FAILURE_2, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 6");
      assertCompileError(FAILURE_3, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 4");
   }
}
