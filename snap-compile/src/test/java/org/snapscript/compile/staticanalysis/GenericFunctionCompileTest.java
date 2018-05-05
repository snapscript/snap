package org.snapscript.compile.staticanalysis;

public class GenericFunctionCompileTest extends CompileTestCase {
   
   private static final String SUCCESS_1 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').longValue();\n";
   
   private static final String SUCCESS_2 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";   
   
   private static final String SUCCESS_3 =
   "var l: List<String> = ['x'];\n"+
   "l.get(0).substring(1);\n"; 
   
   private static final String SUCCESS_4 =
   "var l: List<Integer> = [1];\n"+
   "l.get(0).doubleValue();\n";    
   
   private static final String SUCCESS_5 =
   "var l: List = [1];\n"+
   "l.get(0).doubleValue();\n";  
   
   private static final String SUCCESS_6 =
   "var m: Map = {'x':'x'};\n"+
   "m.get('x').substring(1);\n";     
         
   private static final String SUCCESS_7 =
   "class Foo{}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<T> extends Blah<T, T>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).substring(1);\n";   
   
   private static final String SUCCESS_8 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<Foo, K>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).func();\n";    
   
   private static final String SUCCESS_9 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, K>{}\n"+
   "var n: Nuh<Foo> = new Nuh<Foo>();\n"+
   "n.funA(1).func();\n";  
   
   private static final String SUCCESS_10 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, K>{}\n"+
   "var n: Nuh = new Nuh();\n"+
   "n.funA(1).func();\n";   
   
   private static final String FAILURE_1 =
   "var m: Map<String, String> = {'x':'x'};\n"+
   "m.get('x').longValue();\n";
   
   private static final String FAILURE_2 =
   "var m: Map<String, Integer> = {'x':1};\n"+
   "m.get('x').substring(1);\n";      
   
   private static final String FAILURE_3 =
   "class Foo{}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<T> extends Blah<T, T>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funA(1).longValue();\n";     
   
   private static final String FAILURE_4 =
   "class Foo{\n"+
   "  func(){}\n"+
   "}\n"+
   "trait Bar<A, B>{\n"+
   "   funA(x): A{}\n"+
   "   funB(x): B{}\n"+
   "}\n"+
   "class Blah<X, Y> with Bar<X, Y>{\n"+
   "   funB(x): String{}\n"+
   "}\n"+
   "class Nuh<K> extends Blah<K, Foo>{}\n"+
   "var n: Nuh<String> = new Nuh<String>();\n"+
   "n.funB(1).func();\n";   
   
   public void testFunctionGenerics() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4); 
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileSuccess(SUCCESS_7);
      assertCompileSuccess(SUCCESS_8);
      assertCompileSuccess(SUCCESS_9);
      assertCompileSuccess(SUCCESS_10);      
      assertCompileError(FAILURE_1, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_2, "Function 'substring(lang.Integer)' not found for 'lang.Integer' in /default.snap at line 2");
      assertCompileError(FAILURE_3, "Function 'longValue()' not found for 'lang.String' in /default.snap at line 11");
      assertCompileError(FAILURE_4, "Function 'func()' not found for 'lang.String' in /default.snap at line 13");       
   }
}
