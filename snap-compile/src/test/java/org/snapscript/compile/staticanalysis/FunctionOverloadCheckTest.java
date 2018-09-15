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
import org.snapscript.core.type.Type;

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
      assertTrue(getFunction(context,"new Fun()", "foo", 1).isCachable());
      assertTrue(getFunction(context,"new Foo()", "foo", 1).isCachable());
      assertFalse(getFunction(context,"new Foo()", "blah", 1).isCachable());
      assertFalse(getFunction(context,"new Foo()", "blah", "xx").isCachable());
      assertTrue(getFunction(context,"new Foo()", "blah", 1, "xx").isCachable());
      assertTrue(getFunction(context,"new Fun()", "toString").isCachable());
      assertTrue(getFunction(context,"new Fun()", "hashCode").isCachable());
      assertTrue(getFunction(context,"new String()", "substring", 1).isCachable());
      assertTrue(getFunction(context,"new String()", "charAt", 1).isCachable());
      assertFalse(getFunction(context,"Integer", "valueOf", 1).isCachable());
      assertFalse(getFunction(context,"new PrintStream(System.out)", "println", 1).isCachable());
      assertTrue(getFunction(context,"new PrintStream(System.out)", "flush").isCachable());
   }

   private FunctionPointer getFunction(Context context, String type, String method, Object... args) throws Exception {
      Scope scope = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope();
      Object object = context.getEvaluator().evaluate(scope, type);
      FunctionCall call = context.getResolver().resolveInstance(scope, object, method, args);
      if(call == null && object instanceof Type) {
         call = context.getResolver().resolveStatic(scope, (Type)object, method, args);
      }
      Field field = call.getClass().getDeclaredField("pointer");
      field.setAccessible(true);
      return (FunctionPointer)field.get(call);
   }
}
