package org.snapscript.compile.staticanalysis;

import java.lang.reflect.Field;

import junit.framework.TestCase;
import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.Executable;
import org.snapscript.compile.StoreContext;
import org.snapscript.compile.StringCompiler;
import org.snapscript.core.Context;
import org.snapscript.core.Reserved;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;

public class FunctionOverloadCheckTest extends TestCase {

   private static final String SOURCE =
   "class Fun{\n"+
   "   foo(i: Integer){\n"+
   "      return i;\n"+
   "   }\n"+
   "}\n"+
   "class Foo extends Fun {\n"+
   "   override foo(i: Integer){\n"+
   "      return i;\n"+
   "   }\n"+
   "   blah(s: String){\n"+
   "      return 'String';\n"+
   "   }\n"+
   "   blah(i: Integer){\n"+
   "      return 'Integer';\n"+
   "   }\n"+
   "   blah(i: Integer, s: String){\n"+
   "      return 'Integer,String';\n"+
   "   }\n"+
   "}\n";

   public void testFunctionResolver() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Executable executable = compiler.compile(SOURCE);
      Model model = new EmptyModel();

      executable.execute(model, false);
      assertTrue(getFunction(context,"Fun", "foo", 1).isCachable());
      assertTrue(getFunction(context,"Foo", "foo", 1).isCachable());
      assertFalse(getFunction(context,"Foo", "blah", 1).isCachable());
      assertFalse(getFunction(context,"Foo", "blah", "xx").isCachable());
      assertTrue(getFunction(context,"Foo", "blah", 1, "xx").isCachable());
      assertTrue(getFunction(context,"Fun", "toString").isCachable());
      assertTrue(getFunction(context,"Fun", "hashCode").isCachable());
   }

   private FunctionPointer getFunction(Context context, String type, String method, Object... args) throws Exception {
      Scope scope = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope();
      Object object = context.getEvaluator().evaluate(scope, "new " + type + "()");
      FunctionCall substring = context.getResolver().resolveInstance(scope, object, method, args);
      Field field = substring.getClass().getDeclaredField("pointer");
      field.setAccessible(true);
      return (FunctionPointer)field.get(substring);
   }
}
