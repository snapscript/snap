package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.core.EmptyModel;

public class FunctionReferenceTest extends TestCase {

   private static final String SOURCE_1 =
   "var s = \"hello\";\n"+
   "//var f = i -> s.substring(i);\n"+
   "var f = s::substring;\n"+
   "var r = f(1);\n"+
   "\n"+
   "println(f);\n";
   
   private static final String SOURCE_2 =
   "class Foo {\n"+
   "   var a,b;\n"+
   "   new(a,b){\n"+
   "      this.a=a;\n"+
   "      this.b=b;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"${a},${b}\";"+
   "   }\n"+
   "}\n"+
   "function create(func) {\n"+
   "   func(1,2);\n"+
   "}\n"+
   "var res = create(Foo::new);\n"+
   "println(res);\n"+
   "assert res.toString() == '1,2';\n";
   
   private static final String SOURCE_3 =
   "var func = String::new;\n"+
   "\n"+
   "assert '' == func();\n"+
   "assert 'hello' == func('hello');\n";
   
   public void testFunctionReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(new EmptyModel());
   }
   
   public void testFunctionReferenceNativeConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      compiler.compile(SOURCE_3).execute(new EmptyModel());
   }
}
