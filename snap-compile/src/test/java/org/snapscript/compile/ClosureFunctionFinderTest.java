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
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.ClosureFunctionFinder;
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
      ConstraintMatcher matcher = context.getMatcher();
      Path path = new Path("/");
      FunctionComparator comparator = new FunctionComparator(matcher);
      Module module = new ContextModule(context, null, path, "yy", "", 1);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(comparator, extractor, loader);
      Parameter parameter = new Parameter("n", loader.loadType(String.class), false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), module, null, false);
      Type type = new InvocationFunction(signature, null, null, null, "xx").getHandle();
      Function function = finder.findFunctional(type);
      
      assertNotNull(function);
   }
}
