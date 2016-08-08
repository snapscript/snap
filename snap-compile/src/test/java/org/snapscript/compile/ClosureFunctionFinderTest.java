package org.snapscript.compile;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.compile.StoreContext;
import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.closure.ClosureFunctionFinder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class ClosureFunctionFinderTest extends TestCase {

   public void testFunctionFinder() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      Module module = new ContextModule(context, "/", "yy", 1);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(loader);
      Signature signature = new Signature(Arrays.asList(new Parameter("n", loader.loadType(String.class))), module);
      Type type = new InvocationFunction(signature, null, null, null, "xx").getDefinition();
      Function function = finder.find(type);
      
      assertNotNull(function);
   }
}
