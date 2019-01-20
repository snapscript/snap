package org.snapscript.compile;

public class AwaitTest extends ScriptTestCase {

   private static final String SUCCESS_1 =
   "async func fun() {\n"+
   "   await foo();\n"+
   "   println('after foo');\n"+
   "}\n"+
   "func foo() {\n"+
   "   let n = Thread.currentThread().getName();\n"+
   "   println(n);\n"+
   "}\n"+
   "let x = fun();\n"+
   "println(x.class);\n"+
   "x.get();\n";

   private static final String SUCCESS_2 =
   "async func fun(n) {\n"+
   "   if(n > 0) {\n"+
   "      await foo();\n"+
   "   }\n"+
   "   println('x');\n"+
   "}\n"+
   "func foo() {\n"+
   "   let n = Thread.currentThread().getName();\n"+
   "   println(n);\n"+
   "}\n"+
   "let x = fun(1);\n"+
   "println(x.class);\n"+
   "x.get();\n";

   private static final String SUCCESS_3 =
   "async func fun(n) {\n"+
   "   if(n > 0) {\n"+
   "      n = await foo();\n"+
   "      println(n);\n"+
   "      assert n == 'result for foo()';\n"+
   "   }\n"+
   "   println('x');\n"+
   "}\n"+
   "func foo() {\n"+
   "   let n = Thread.currentThread().getName();\n"+
   "   println(n);\n"+
   "   return 'result for foo()';\n"+
   "}\n"+
   "let x = fun(1);\n"+
   "println(x.class);\n"+
   "x.get();\n";

   private static final String SUCCESS_4 =
   "async func fun(n) {\n"+
   "   if(n > 0) {\n"+
   "      let f = await foo();\n"+
   "      println(f);\n"+
   "      assert f == 'result for foo()';\n"+
   "   }\n"+
   "   println('x');\n"+
   "}\n"+
   "func foo() {\n"+
   "   let n = Thread.currentThread().getName();\n"+
   "   println(n);\n"+
   "   return 'result for foo()';\n"+
   "}\n"+
   "let x = fun(1);\n"+
   "println(x.class);\n"+
   "x.get();\n";

   private static final String SUCCESS_5 =
   "async func fun(n) {\n"+
    "   if(n > 0) {\n"+
    "      let f: String = await foo();\n"+
    "      println(f);\n"+
    "      assert f == 'result for foo()';\n"+
    "   }\n"+
    "   println('x');\n"+
    "}\n"+
    "func foo() {\n"+
    "   let n = Thread.currentThread().getName();\n"+
    "   println(n);\n"+
    "   return 'result for foo()';\n"+
    "}\n"+
    "let x = fun(1);\n"+
    "println(x.class);\n"+
    "x.get();\n";

   private static final String SUCCESS_6 =
   "async func fun(n) {\n"+
   "   if(n > 0) {\n"+
   "      const f: String = await foo();\n"+
   "      println(f);\n"+
   "      assert f == 'result for foo()';\n"+
   "   }\n"+
   "   println('x');\n"+
   "}\n"+
   "func foo() {\n"+
   "   let n = Thread.currentThread().getName();\n"+
   "   println(n);\n"+
   "   return 'result for foo()';\n"+
   "}\n"+
   "let x = fun(1);\n"+
   "println(x.class);\n"+
   "x.get();\n";

   public void testAwait() throws Exception {
      assertScriptExecutes(SUCCESS_1);
      assertScriptExecutes(SUCCESS_2);
      assertScriptExecutes(SUCCESS_3);
      assertScriptExecutes(SUCCESS_4);
      assertScriptExecutes(SUCCESS_5);
      assertScriptExecutes(SUCCESS_6);
   }

   @Override
   public boolean isThreadPool() {
      return true;
   }
}
