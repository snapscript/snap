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

   private static final String SUCCESS_7 =
   "async func fun(n) {\n"+
   "   if(n > 0) {\n"+
   "      const f: String = await foo();\n"+
   "      println(f);\n"+
   "      assert f == 'result for foo()';\n"+
   "      return f;\n"+
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
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_8 =
   "module Mod {\n"+
   "   async fun(n) {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = Mod.fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_9 =
   "class Typ {\n"+
   "   async static fun(n) {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   static foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = Typ.fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_10 =
   "class Typ {\n"+
   "   async fun(n) {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_11 =
   "class Typ {\n"+
   "   async fun(n): Promise<Integer> {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_12 =
   "class Typ {\n"+
   "   async fun(n): Promise {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for foo()';\n" +
   "}).get() == 'result for foo()';\n";

   private static final String SUCCESS_13 =
   "class Typ {\n"+
   "   async fun(n): Promise {\n"+
   "      return `result for fun(${n})`;\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 'result for foo()';\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1);\n"+
   "println(x.class);\n"+
   "assert x.thenAccept(y -> {\n"+
   "   println(`RESULT=${y}`);\n"+
   "   assert y == 'result for fun(1)';\n" +
   "}).get() == 'result for fun(1)';\n";

   private static final String SUCCESS_14 =
   "class Typ {\n"+
   "   async fun(n): Promise {\n"+
   "      if(n > 0) {\n"+
   "         const f: String = await foo();\n"+
   "         println(f);\n"+
   "         assert f == 'result for foo()';\n"+
   "         return f;\n"+
   "      }\n"+
   "      println('x');\n"+
   "   }\n"+
   "   foo() {\n"+
   "      throw new RuntimeException('error occured in foo()');\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1);\n"+
   "println(x.class);\n"+
   "x.thenCatch(y -> {\n"+
   "   y.printStackTrace();\n"+
   "   println(`ERROR=${y}`);\n"+
   "   println(`ERROR=${y.class}`);\n"+
   "   println(`ERROR=${y.message}`);\n"+
   "   assert y instanceof Exception;\n" +
   "}).join();\n";

   private static final String SUCCESS_15 =
   "class Typ {\n"+
    "   async fun(n): Promise<Integer> {\n"+
    "      if(n > 0) {\n"+
    "         const f: Integer = await foo();\n"+
    "         println(f);\n"+
    "         assert f == 11;\n"+
    "         return f;\n"+
    "      }\n"+
    "      println('x');\n"+
    "   }\n"+
    "   foo() {\n"+
    "      let n = Thread.currentThread().getName();\n"+
    "      println(n);\n"+
    "      return 11;\n"+
    "   }\n"+
    "}\n"+
    "assert new Typ().fun(1).get().intValue() == 11;\n";

   private static final String SUCCESS_16 =
   "class Typ {\n"+
   "   async fun(n): Promise {\n"+
   "      await foo();\n"+
   "   }\n"+
   "   foo() {\n"+
   "      let n = Thread.currentThread().getName();\n"+
   "      println(n);\n"+
   "      return 11;\n"+
   "   }\n"+
   "}\n"+
   "let x = new Typ().fun(1).get();\n" +
   "println(x);";

   private static final String SUCCESS_17 =
   "async func foo(): Promise<Integer> {\n"+
   "   for(i in 0 to 10) {\n"+
   "      println(i);\n"+
   "      await i;\n"+
   "      println('i='+Thread.currentThread().getName());\n"+
   "   }\n"+
   "   println('done');\n"+
   "   return 13356;\n"+
   "}\n"+
   "println('x='+Thread.currentThread().getName());\n"+
   "assert foo().get().intValue() == 13356;\n";


   private static final String SUCCESS_18 =
   "async func fun(): Promise<Integer> {\n"+
   "   return await foo();\n" +
   "}\n"+
   "func foo() {\n"+
   "  let n = Thread.currentThread().getName();\n"+
   "  println(n);\n"+
   "  return 112;\n"+
   "}\n"+
   "println('x='+Thread.currentThread().getName());\n"+
   "assert fun().get().intValue() == 112;\n";

   private static final String SUCCESS_19 =
   "async func fun(): Promise<String> {\n"+
   "   for(i in 0 to 10){\n"+
   "      return await foo(i);\n" +
   "   }\n"+
   "}\n"+
   "func foo(i) {\n"+
   "   return `foo(${i})`;\n"+
   "}\n"+
   "println('x='+Thread.currentThread().getName());\n"+
   "assert fun().get().toLowerCase() == `foo(0)`;\n";

   public void testAwait() throws Exception {
      assertScriptExecutes(SUCCESS_1);
      assertScriptExecutes(SUCCESS_2);
      assertScriptExecutes(SUCCESS_3);
      assertScriptExecutes(SUCCESS_4);
      assertScriptExecutes(SUCCESS_5);
      assertScriptExecutes(SUCCESS_6);
      assertScriptExecutes(SUCCESS_7);
      assertScriptExecutes(SUCCESS_8);
      assertScriptExecutes(SUCCESS_9);
      assertScriptExecutes(SUCCESS_10);
      assertScriptExecutes(SUCCESS_11);
      assertScriptExecutes(SUCCESS_12);
      assertScriptExecutes(SUCCESS_13);
      assertScriptExecutes(SUCCESS_14);
      assertScriptExecutes(SUCCESS_15);
      assertScriptExecutes(SUCCESS_16);
      assertScriptExecutes(SUCCESS_17);
      assertScriptExecutes(SUCCESS_18);
      assertScriptExecutes(SUCCESS_19);
   }

   @Override
   public boolean isThreadPool() {
      return true;
   }
}
