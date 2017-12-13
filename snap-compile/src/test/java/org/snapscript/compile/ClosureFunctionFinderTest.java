package org.snapscript.compile;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionFinder;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ClosureFunctionFinderTest extends TestCase {

   public void testFunctionFinder() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      TypeExtractor extractor = context.getExtractor();
      Path path = new Path("/");
      Module module = new ContextModule(context, null, path, "yy", 1);
      FunctionFinder finder = new FunctionFinder(extractor, loader);
      Parameter parameter = new Parameter("n", loader.loadType(String.class), false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), module, null, false);
      Type type = new InvocationFunction(signature, null, null, null, "xx").getHandle();
      Function function = finder.find(type);
      
      assertNotNull(function);
   }
}
