package org.snapscript.compile;

public class AwaitCascadeTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "async func one(){\n"+
   "   println(`one(): ` +Thread.currentThread().getName());\n"+
   "   let a1 = await two();\n"+
   "   let b1 = await two();\n"+
   "   return `one: ${a1}---${b1}`;\n"+
   "}\n"+
   "async func two(){\n"+
   "   println(`two(): ` +Thread.currentThread().getName());\n"+
   "   let a2 = await three();\n"+
   "   return `two: ${a2}`;\n"+
   "}\n"+
   "async func three(){\n"+
   "   println(`three(): ` +Thread.currentThread().getName());\n"+
   "   let a3 = await 'foo'.toUpperCase();\n"+
   "   return `three: ${a3}`;\n"+
   "}\n"+
   "one().join().success(this::println);\n"+
   "one().join().success(result -> {\n"+
   "   assert result == 'one: two: three: FOO---two: three: FOO';\n"+
   "});\n";

   private static final String SOURCE_2 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   return 'two';\n"+
   "}\n"+
   "one(100).join().success(this::println);\n";

   private static final String SOURCE_3 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let list = [];\n"+
   "one(1000).join().failure(e -> list.add(e));\n"+
   "assert list.length == 1;\n"+
   "assert list[0] instanceof Exception;\n"+
   "list[0].printStackTrace();\n";

   private static final String SOURCE_4 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one(100).value;\n"+
   "} catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "assert failure;\n";

   public void testAwaitCascade() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
   }

   @Override
   public boolean isThreadPool() {
      return true;
   }
}
